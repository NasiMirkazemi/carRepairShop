package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.dto.AppointmentDto;
import com.first.carRepairShop.entity.Appointment;
import com.first.carRepairShop.entity.AppointmentStatus;
import com.first.carRepairShop.entity.Customer;
import com.first.carRepairShop.entity.NotificationType;
import com.first.carRepairShop.event.AppointmentConfirmEvent;
import com.first.carRepairShop.event.AppointmentEventProducer;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.InvalidAppointmentTimeException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.mapper.AppointmentMapper;
import com.first.carRepairShop.repository.AppointmentRepository;
import com.first.carRepairShop.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AppointmentService {
    private static final String REQUESTED_CACHE = "CR:appointments:requested";
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final CustomerRepository customerRepository;
    private final AppointmentRequestRedisService appointmentRequestRedisService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final NotificationService notificationService;
    private final AppointmentEventProducer appointmentEventProducer;
    private final Logger logger= LoggerFactory.getLogger(AppointmentService.class);

    @Scheduled(fixedRate = 30000) // Runs every 20 minutes (1,200,000 ms)
    public void autoConfirmAppointment() {
        logger.info("Auto-confirm appointment task started...");
        Set<String> keys = redisTemplate.keys(REQUESTED_CACHE+"*");
        if (keys != null && !keys.isEmpty()) {
            logger.info("found {} keys in redis for Auto-confirm"+keys.size());
            keys.stream()
                    .filter(key -> !key.isEmpty())
                    .map( appointmentRequestRedisService::getAppointmentByKey)
                    .filter(Objects::nonNull)            //.filter(obj -> Objects.nonNull(obj))
                    .forEach(appointmentDto ->{
                        logger.info("Auto-confirming appointment with number: {}"+ appointmentDto.getAppointmentNumber()); // Log each appointment
                        confirmAppointmentForAuto(appointmentDto);} );
        }else {
            logger.info("No appointments found in Redis for auto-confirmation.");
        }
    }

    @Transactional
    public AppointmentDto confirmAppointmentManually(String appointmentNumber) {
        AppointmentDto appointmentDto = appointmentRequestRedisService.getAppointmentByNumber(appointmentNumber);
        if (appointmentDto == null) {
            throw new NotFoundException("Requested appointment not found in Redis.");
        }
        Appointment appointment = appointmentMapper.toAppointmentEntity(appointmentDto);
        //Fetch only confirm appointment
        appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
        appointmentRepository.save(appointment);
        appointmentRequestRedisService.deleteRequestedAppointmentByNumber(appointmentNumber);
        Customer customer = customerRepository.findById(appointment.getCustomerId())
                .orElseThrow(() -> new NotFoundException("No customer find by id: " + appointment.getCustomerId()));
        AppointmentConfirmEvent appointmentConfirmEvent = new AppointmentConfirmEvent(
                appointment.getAppointmentNumber(),
                appointment.getCustomerId(),
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime());
        appointmentEventProducer.publishEvent(appointmentConfirmEvent);

        notificationService.sendConfirmAppointmentNotification(
                NotificationType.APPOINTMENT_CONFIRMED,
                customer.getName(), customer.getLastname(), customer.getEmail(),appointment.getAppointmentDate(),appointment.getAppointmentTime());
        return appointmentMapper.toAppointmentDto(appointment);
    }
    @Transactional
    public AppointmentDto confirmAppointmentForAuto( AppointmentDto appointmentDto) {
        if (appointmentDto == null) {
            throw new BadRequestException("Appointment Dto is required.");
        }
        Appointment appointment = appointmentMapper.toAppointmentEntity(appointmentDto);
        //Fetch only confirm appointment
        appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
        appointmentRepository.save(appointment);
        appointmentRequestRedisService.deleteRequestedAppointmentByNumber(appointment.getAppointmentNumber());
        Customer customer = customerRepository.findById(appointment.getCustomerId())
                .orElseThrow(() -> new NotFoundException("No customer find by id: " + appointment.getCustomerId()));
        AppointmentConfirmEvent appointmentConfirmEvent = new AppointmentConfirmEvent(
                appointment.getAppointmentNumber(),
                appointment.getCustomerId(),
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime());
        appointmentEventProducer.publishEvent(appointmentConfirmEvent);

        notificationService.sendConfirmAppointmentNotification(
                NotificationType.APPOINTMENT_CONFIRMED,
                customer.getName(), customer.getLastname(), customer.getEmail(),appointment.getAppointmentDate(),appointment.getAppointmentTime());
        return appointmentMapper.toAppointmentDto(appointment);
    }

    public List<LocalTime> generateTimeSlots() {
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(13, 0);
        Duration slotDuration = Duration.ofMinutes(30);
        List<LocalTime> allSlotTime = new ArrayList<>();
        LocalTime currentTime = startTime;
        while (currentTime.isBefore(endTime)) {
            allSlotTime.add(currentTime);
            currentTime = currentTime.plus(slotDuration);
        }
        return allSlotTime;
    }

    public List<LocalTime> getAvailableTimeSlots(LocalDate appointmentDate) {
        List<LocalTime> allTimeSlots = generateTimeSlots();
        List<LocalTime> bookedTimeSlots = appointmentRepository.findBookTimeSlots(appointmentDate, AppointmentStatus.CONFIRMED);
        List<LocalTime> availableTimeSlots = allTimeSlots.stream()
                .filter(slot -> !bookedTimeSlots.contains(slot))
                .collect(Collectors.toList());
        if (availableTimeSlots.isEmpty()) {
            throw new NotFoundException("all Time Slots are booked on date:" + appointmentDate);
        }
        return availableTimeSlots;
    }

    @Transactional
    public AppointmentDto cancelAppointment(String appointmentNumber) {
        Appointment appointment = appointmentRepository.findByAppointmentNumber(appointmentNumber)
                .orElseThrow(() -> new NotFoundException("No appointment found for appointment number:" + appointmentNumber));
        appointment.setAppointmentStatus(AppointmentStatus.CANCELED);
        appointmentRepository.save(appointment);
        return appointmentMapper.toAppointmentDto(appointment);
    }

    @Transactional
    public AppointmentDto updateAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = appointmentRepository.findById(appointmentDto.getAppointmentId())
                .orElseThrow(() -> new NotFoundException("No appointment found with ID: " + appointmentDto.getAppointmentId()));
        if (appointment.getAppointmentStatus() != AppointmentStatus.CONFIRMED) {
            throw new IllegalStateException("only confirmed Appointment can be updated");
        }
        if (!appointment.getAppointmentDate().equals(appointmentDto.getAppointmentDate())) {
            List<LocalTime> availableTimeSlots = getAvailableTimeSlots(appointmentDto.getAppointmentDate());
            if (!availableTimeSlots.contains(appointmentDto.getAppointmentTime())) {
                throw new InvalidAppointmentTimeException("Selected time slot is not available. Available slots: ", availableTimeSlots);
            }
        }
        appointmentMapper.updateAppointmentFromDto(appointmentDto, appointment);
        appointmentRepository.save(appointment);
        return appointmentMapper.toAppointmentDto(appointment);
    }

    public AppointmentDto getAppointmentById(Integer appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException("No appointment found with ID: " + appointmentId));
        return appointmentMapper.toAppointmentDto(appointment);
    }

    public List<AppointmentDto> getAllAppointments() {
        List<Appointment> appointmentList = appointmentRepository.findAll();
        if ( appointmentList.isEmpty()) {
            throw new NotFoundException("No Appointment exist");
        }
        return appointmentMapper.toAppointmentDtoList(appointmentList);
    }

    public List<AppointmentDto> getAppointmentsByDate(LocalDate appointmentDate) {
        List<Appointment> appointmentList = appointmentRepository.findByAppointmentDate(appointmentDate);
        if (appointmentList == null || appointmentList.isEmpty()) {
            throw new NotFoundException("No Appointment exist for date:" + appointmentDate);
        }
        return appointmentMapper.toAppointmentDtoList(appointmentList);
    }

    public List<AppointmentDto> getAllByCustomerId(Integer customerId) {
        List<Appointment> appointmentList = appointmentRepository.findByCustomerId(customerId);
        if (appointmentList == null || appointmentList.isEmpty()) {
            throw new NotFoundException("No Appointment exist For customer with Id" + customerId);
        }
        return appointmentMapper.toAppointmentDtoList(appointmentList);
    }


}
