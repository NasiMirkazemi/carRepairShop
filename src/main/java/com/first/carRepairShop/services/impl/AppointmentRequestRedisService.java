package com.first.carRepairShop.services.impl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.first.carRepairShop.dto.AppointmentDto;
import com.first.carRepairShop.entity.Car;
import com.first.carRepairShop.entity.Customer;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.repository.AppointmentRepository;
import com.first.carRepairShop.repository.CarRepository;
import com.first.carRepairShop.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentRequestRedisService {
    private static final String REQUESTED_CACHE = "CR:appointments:requested:";
    private final RedisTemplate<String, Object> redisTemplate;
    private final AppointmentRepository appointmentRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;
    private final Logger logger=LoggerFactory.getLogger(AppointmentRequestRedisService.class);

    /*	 It is used when you want to put the result into the cache,
     regardless of whether it already exists.
      It is used for updating or adding values to the cache after executing the method.
     */
    public AppointmentDto reserveAppointmentRequest(AppointmentDto appointmentDto) {
        if (appointmentDto.getCustomerId() == null || appointmentDto.getCarId() == null) {
            throw new BadRequestException("CustomerId and CarId is required");
        }
        if (appointmentDto.getAppointmentDate() == null || appointmentDto.getAppointmentTime() == null) {
            throw new BadRequestException(" appointment date and appointment time is required");
        }
        Customer customer = customerRepository.findById(appointmentDto.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Customer  Not found with id:" + appointmentDto.getCustomerId()));
        Car car = carRepository.findById(appointmentDto.getCarId())
                .orElseThrow(() -> new NotFoundException("Car Not found whit id:" + appointmentDto.getCarId()));
        if (!car.getCustomer().getId().equals(customer.getId())) {
            throw new BadRequestException("car whit id:" + car.getCarId() + "does not belong to customer whit id:" + customer.getId());
        }
        String appointmentNumber = generateAppointmentNumber(
                appointmentDto.getAppointmentDate(),
                appointmentDto.getCustomerId(),
                appointmentDto.getCarId());
        appointmentDto.setAppointmentNumber(appointmentNumber);
        redisTemplate.opsForValue().set(REQUESTED_CACHE + appointmentNumber, appointmentDto,
                120, TimeUnit.MINUTES);
        return appointmentDto;
    }

    /*@Cacheable(value = REQUESTED_CACHE, key = "#appointmentNumber", unless = "#result==null")
    public AppointmentDto getRequestedAppointment(String key) {
        Object cachedValue = getRequestedAppointmentByNu(key);
        if (cachedValue == null) {
            return null;
        }
        AppointmentDto appointmentDto = objectMapper.convertValue(cachedValue, AppointmentDto.class);
        return appointmentDto;
    }*/

    @CacheEvict(value = REQUESTED_CACHE, key = "#appointmentNumber")
    public String removeRequestedAppointment(String appointmentNumber) {

        return "Appointment with number " + appointmentNumber + " is deleted";
    }

    @CachePut(value = REQUESTED_CACHE, key = "#appointmentDto.appointmentNumber")
    public AppointmentDto updateAppointmentRequest(AppointmentDto appointmentDto) {
        return appointmentDto;
    }

    public AppointmentDto getAppointmentByNumber(String appointmentNumber) {
        Object cachedValue = redisTemplate.opsForValue().get(appointmentNumber);
        if (cachedValue == null) {
            throw new NotFoundException("No appointment found in Redis for appointmentNumber: " + appointmentNumber);
        }
        AppointmentDto appointmentDto = objectMapper.convertValue(cachedValue, AppointmentDto.class);
        return appointmentDto;

    }
    public AppointmentDto getAppointmentByKey(String key){
         Object cachedValue=redisTemplate.opsForValue().get(key);
         if (cachedValue==null){
             logger.warn("No appointment found in Redis for key: {}"+key);
             throw new NotFoundException("No appointment found in Redis for appointment with key:"+key);
         }
          AppointmentDto appointmentDto=objectMapper.convertValue(cachedValue,AppointmentDto.class);
        logger.info("Successfully fetched appointment from Redis for key: {}"+ key);
        return appointmentDto;
    }


    public void deleteRequestedAppointmentByNumber(String appointmentNumber) {
        String key = REQUESTED_CACHE + appointmentNumber;
        Boolean isDelete = redisTemplate.delete(key);
        if (Boolean.FALSE.equals(isDelete)) {
            throw new NotFoundException("No appointment found in Redis for appointmentNumber:" + appointmentNumber);
        }
        System.out.println("Appointment deleted for appointmentNumber: " + appointmentNumber);
    }

    /*opsForValue() is a specific method provided by RedisTemplate for performing operations on String values in Redis*/
    public List<AppointmentDto> getAllRequestedAppointments() {
        Set<String> keys = redisTemplate.keys(REQUESTED_CACHE + "*");
        if (keys == null && keys.isEmpty()) {
            return Collections.emptyList();
        }
        return keys.stream()
                .map(key -> (AppointmentDto) redisTemplate.opsForValue().get(key))//.map(redisTemplate.opsForValue()::get)
                .collect(Collectors.toList());

    }

    @CacheEvict(value = REQUESTED_CACHE, allEntries = true)
    public void deleteAllRequestedAppointment() {
        System.out.println("All requested appointments have been deleted from Redis.");
    }

    private String generateAppointmentNumber(LocalDate appointmentDate, Integer customerId, Integer carId) {
        String date = appointmentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        boolean exist = appointmentRepository.existsByCustomerIdAndCarIdAndAppointmentDate(customerId, carId, appointmentDate);
        if (exist) {
            throw new BadRequestException("appointment for this Customer and this car is already exist  on date:" + appointmentDate);
        }
        String sequenceKey = "appointment:counter:" + date;
        Long sequence = redisTemplate.opsForValue().increment(sequenceKey, 1);
        String formattedSequence = String.format("%03d", sequence);
        return "AP" + date + "-" + formattedSequence;
    }
}
