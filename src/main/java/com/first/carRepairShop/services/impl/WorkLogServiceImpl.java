package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.associations.ItemDetail;
import com.first.carRepairShop.associations.ServiceDetail;
import com.first.carRepairShop.dto.ItemDetailDto;
import com.first.carRepairShop.dto.ServiceDetailDto;
import com.first.carRepairShop.dto.TotalHoursForWorKLog;
import com.first.carRepairShop.dto.WorkLogDto;
import com.first.carRepairShop.dto.websocket.WorkLogNotificationMessage;
import com.first.carRepairShop.entity.*;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.InvalidWorkLogStatusException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.mapper.ItemDetailMapper;
import com.first.carRepairShop.mapper.ServiceDetailMapper;
import com.first.carRepairShop.mapper.WorkLogMapper;
import com.first.carRepairShop.repository.AssignmentRepository;
import com.first.carRepairShop.repository.MechanicRepository;
import com.first.carRepairShop.repository.UserRepository;
import com.first.carRepairShop.repository.WorkLogRepository;
import com.first.carRepairShop.services.InventoryService;
import com.first.carRepairShop.services.WorkLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkLogServiceImpl implements WorkLogService {
    private final WorkLogRepository workLogRepository;
    private final WorkLogMapper workLogMapper;
    private final AssignmentRepository assignmentRepository;
    private final MechanicRepository mechanicRepository;
    private final ServiceDetailMapper serviceDetailMapper;
    private final ItemDetailMapper itemDetailMapper;
    private final InventoryService inventoryService;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    private final Logger logger = LoggerFactory.getLogger(WorkLogServiceImpl.class);

    @Transactional
    @PreAuthorize("hasAuthority(T(com.first.carRepairShop.security.WorkLogPermission).ADD)")
    public WorkLogDto addWorkLog(WorkLogDto workLogDto) {
        if (workLogDto.getAssignmentId() == null) {
            throw new BadRequestException("Creating workLog required An Assignment");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserApp userApp = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found with username :" + username));
        String roleName = userApp.getRole().getName();
        if (!userApp.getRole().getName().equals("MECHANIC")) {
            throw new AccessDeniedException("Only mechanics can add work logs.");
        }
        Integer mechanicId = userApp.getId();
        Mechanic mechanic = mechanicRepository.findById(mechanicId)
                .orElseThrow(() -> new NotFoundException("No Mechanic found with id : " + mechanicId));
        Assignment assignment = assignmentRepository.findById(workLogDto.getAssignmentId())
                .orElseThrow(() -> new NotFoundException("No Assignment found with id : " + workLogDto.getAssignmentId()));

        WorkLog workLogEntity = workLogMapper.toEntity(workLogDto);
        workLogEntity.setMechanic(mechanic);
        workLogEntity.setPerformedService(new ArrayList<>());
        workLogEntity.setUsedItem(new ArrayList<>());
        workLogEntity.setCreateDate(LocalDate.now());
        String workLogNumber = generateWorkLogNumber();
        workLogEntity.setWorkLogNumber(workLogNumber);
        workLogEntity.setWorkLogStatus(WorkLogStatus.PLANNED);
        assignment.getWorkLogs().add(workLogEntity);
        WorkLog savedWorkLog = workLogRepository.save(workLogEntity);
        WorkLogNotificationMessage message = WorkLogNotificationMessage.builder()
                .type("Add WorkLog")
                .assignmentId(savedWorkLog.getAssignment().getAssignmentId())
                .workLogId(savedWorkLog.getWorkLogId())
                .message(" Work Log is Successfully added ")
                .localDateTime(LocalDateTime.now())
                .build();
        simpMessagingTemplate.convertAndSendToUser(userApp.getUsername(), "/queue/workLog", message);
        return workLogMapper.toDto(savedWorkLog);
    }

    @PreAuthorize("hasAuthority(T(com.first.carRepairShop.security.WorkLogPermission).START)")
    public void startWorkLog(Integer workLogId) {
        WorkLog workLog = workLogRepository.findById(workLogId)
                .orElseThrow(() -> new NotFoundException("No work Log found with id:" + workLogId));
        if (workLog.getWorkLogStatus() != WorkLogStatus.PLANNED) {
            throw new BadRequestException("Can only start a PLANNED WorkLog");
        }
        workLog.setCheckIn(LocalDateTime.now());
        workLog.setWorkLogStatus(WorkLogStatus.IN_PROGRESS);
        workLogRepository.save(workLog);
    }

    @PreAuthorize("hasAuthority(T(com.first.carRepairShop.security.WorkLogPermission).PAUSE)")
    public void pauseWorkLog(Integer workLogId) {
        WorkLog workLog = workLogRepository.findById(workLogId)
                .orElseThrow(() -> new NotFoundException("No work Log found with id:" + workLogId));
        if (workLog.getWorkLogStatus() != WorkLogStatus.IN_PROGRESS) {
            throw new BadRequestException("Can only start a PLANNED WorkLog");
        }
        workLog.setPauseStart(LocalDateTime.now());
        workLog.setWorkLogStatus(WorkLogStatus.PAUSED);
        workLogRepository.save(workLog);
    }

    @PreAuthorize("hasAuthority(T(com.first.carRepairShop.security.WorkLogPermission).RESUME)")
    public void resumeWorkLog(Integer workLogId) {
        WorkLog workLog = workLogRepository.findById(workLogId)
                .orElseThrow(() -> new NotFoundException("No work Log found with id:" + workLogId));
        if (workLog.getWorkLogStatus() != WorkLogStatus.PAUSED) {
            throw new BadRequestException("Can only resume a Pause WorkLog");
        }
        workLog.setPauseEnd(LocalDateTime.now());
        workLog.setWorkLogStatus(WorkLogStatus.RESUME);
        workLogRepository.save(workLog);
    }

    @PreAuthorize("hasAuthority(T(com.first.carRepairShop.security.WorkLogPermission).COMPLETE)")
    public void completeWorkLog(Integer workLogId) {
        WorkLog workLog = workLogRepository.findById(workLogId)
                .orElseThrow(() -> new NotFoundException("No work Log found with id:" + workLogId));
        if (workLog.getWorkLogStatus() == WorkLogStatus.COMPLETED) {
            throw new BadRequestException("WorkLog is already completed.");
        }
        if (workLog.getWorkLogStatus() != WorkLogStatus.IN_PROGRESS && workLog.getWorkLogStatus() != WorkLogStatus.RESUME) {
            throw new BadRequestException("WorkLog must be in IN_PROGRESS or RESUMED state to be completed.");
        }
        workLog.setCheckOut(LocalDateTime.now());
        calculateTotalHour(workLog);
        workLog.setWorkLogStatus(WorkLogStatus.COMPLETED);
        workLogRepository.save(workLog);
    }

    @PreAuthorize("hasAuthority(T(com.first.carRepairShop.security.WorkLogPermission).CANCEL)")
    public void cancelWorkLog(Integer workLogId) {
        WorkLog workLog = workLogRepository.findById(workLogId)
                .orElseThrow(() -> new NotFoundException("No work Log found with id:" + workLogId));
        if (workLog.getWorkLogStatus() != WorkLogStatus.PLANNED) {
            throw new BadRequestException("Only PLANNED work logs can be cancelled.");
        }
        workLog.setCancelTime(LocalDateTime.now());
        workLog.setWorkLogStatus(WorkLogStatus.CANCELLED);
        workLogRepository.save(workLog);
    }


    @PreAuthorize("hasAuthority(T(com.first.carRepairShop.security.WorkLogPermission).ADD_ITEM)")
    public ItemDetailDto addToUsedItem(Integer workLogId, ItemDetailDto itemDetailDto,int quantityUsed) {
        if (workLogId == null)
            throw new BadRequestException("Work Id is required");
        if (itemDetailDto == null)
            throw new BadRequestException("item Detail is required");
        WorkLog workLog = workLogRepository.findById(workLogId)
                .orElseThrow(() -> new NotFoundException("No work Log found with id:" + workLogId));
        if (workLog.getWorkLogStatus() == WorkLogStatus.IN_PROGRESS
                || workLog.getWorkLogStatus() == WorkLogStatus.PAUSED
                || workLog.getWorkLogStatus() == WorkLogStatus.RESUME) {
            ItemDetail itemDetail = itemDetailMapper.toItemDetail(itemDetailDto);
            workLog.getUsedItem().add(itemDetail);
            inventoryService.decreaseStock(itemDetail.getItemId(),quantityUsed,workLogId);
            logger.info("Item [{}] added to used items for WorkLog [{}]", itemDetail, workLogId);
            workLogRepository.save(workLog);
            return itemDetailMapper.toItemDetailDto(itemDetail);
        } else {
            throw new InvalidWorkLogStatusException("Cannot add item when work is not active.");
        }
    }

    @PreAuthorize("hasAuthority(T(com.first.carRepairShop.security.WorkLogPermission).ADD_SERVICE)")
    @Transactional
    public ServiceDetailDto addServiceToPerformedServices(Integer workLogId, ServiceDetailDto serviceDetailDto) {
        if (serviceDetailDto == null)
            throw new BadRequestException("Service Detail is required");
        if (workLogId == null)
            throw new BadRequestException("Work Log id is required");
        WorkLog workLog = workLogRepository.findById(workLogId)
                .orElseThrow(() -> new NotFoundException("No work Log found with id:" + workLogId));
        if (workLog.getWorkLogStatus() == WorkLogStatus.IN_PROGRESS
                || workLog.getWorkLogStatus() == WorkLogStatus.RESUME) {
            ServiceDetail serviceDetail = serviceDetailMapper.toServiceDetail(serviceDetailDto);
            workLog.getPerformedService().add(serviceDetail);
            logger.info("Service[{}] added to performed services for WorkLog[{}]", serviceDetail, workLogId);
            workLogRepository.save(workLog);
            return serviceDetailMapper.toServiceDetailDto(serviceDetail);
        } else {
            throw new InvalidWorkLogStatusException("Cannot add service unless work is in progress/resume." +
                    " Current status:" + workLog.getWorkLogStatus());
        }

    }

    @PreAuthorize("hasAuthority(T(com.first.carRepairShop.security.WorkLogPermission).GET_DETAIL)")
    public WorkLogDto getWorkLogById(Integer workLogId) {
        if (workLogId == null)
            throw new BadRequestException("Work log id is required");
        WorkLog workLog = workLogRepository.findById(workLogId)
                .orElseThrow(() -> new NotFoundException("No Work Log found with id:" + workLogId));
        return workLogMapper.toDto(workLog);
    }

    public List<WorkLogDto> getAllWorkLog() {
        List<WorkLog> workLogList = workLogRepository.findAll();
        return workLogMapper.toDtoList(workLogList);
    }

    public List<WorkLogDto> listWorkLogsByStatus(WorkLogStatus workLogStatus) {
        if (workLogStatus == null)
            throw new BadRequestException("Work Log Status is required");
        List<WorkLog> workLogList = workLogRepository.findAllByWorkLogStatus(workLogStatus);
        return workLogMapper.toDtoList(workLogList);
    }

    public List<WorkLogDto> listWorkLogsByDateRange(LocalDate from, LocalDate to) {
        if (from == null)
            throw new BadRequestException("From Date is required");
        if (to == null)
            throw new BadRequestException("To Date is required");
        List<WorkLog> workLogList = workLogRepository.findByCreateDateBetween(from, to);
        return workLogMapper.toDtoList(workLogList);

    }
    @Transactional
    @PreAuthorize("hasAuthority(T(com.first.carRepairShop.security.WorkLogPermission).GET_ALL_MECHANIC_ID)")
    public List<WorkLogDto> listWorkLogsByMechanic(Integer mechanicId) {
        if (mechanicId == null)
            throw new BadRequestException("Mechanic Id ir required");
        Mechanic mechanic = mechanicRepository.findById(mechanicId)
                .orElseThrow(() -> new NotFoundException("No Mechanic found by id." + mechanicId));
        List<WorkLog> workLogList = mechanic.getWorkLogs();
        if (workLogList.isEmpty()) {
            logger.info("No work logs found for mechanic with ID: {}", mechanicId);
        }
        return workLogMapper.toDtoList(workLogList);

    }

    private String generateWorkLogNumber() {
        LocalDate today = LocalDate.now();
        Long count = workLogRepository.countByCreateDate(today) + 1;
        String formattedCount = String.format("%d", count);
        String datePart = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return "WL" + datePart + "-" + formattedCount;

    }

    private void calculateTotalHour(WorkLog workLog) {
        if (workLog.getCheckIn() != null && workLog.getCheckOut() != null) {
            Long durationInMinutes = Duration.between(workLog.getCheckIn(), workLog.getCheckOut()).toMinutes();

            Long pauseDurationInMinutes = 0L;
            if (workLog.getPauseStart() != null && workLog.getPauseEnd() != null) {
                pauseDurationInMinutes = Duration.between(workLog.getPauseStart(), workLog.getPauseEnd()).toMinutes();
            }
            double totalHours = (durationInMinutes - pauseDurationInMinutes) / 60.0;
            workLog.setTotalHours(totalHours);
            logger.info("Calculated total hours for WorkLog {}: {} hours", workLog.getWorkLogId(), totalHours);

        } else {
            workLog.setTotalHours(0.0);
            logger.warn("Missing check-in or check-out time for WorkLog {}", workLog.getWorkLogId());
        }
    }

    @PreAuthorize("hasAuthority(T(com.first.carRepairShop.security.WorkLogPermission).CALCULATE_TOTAL_HOUR_WORKLOG)")
    public TotalHoursForWorKLog calculateTotalHourForView(Integer workLogId) {
        if (workLogId == null)
            throw new BadRequestException("work Log id is required");
        WorkLog workLog = workLogRepository.findById(workLogId)
                .orElseThrow(() -> new NotFoundException("No Work Log found with id : " + workLogId));
        double totalHours = 0;
        if (workLog.getCheckIn() != null && workLog.getCheckOut() != null) {
            Long durationInMinutes = Duration.between(workLog.getCheckIn(), workLog.getCheckOut()).toMinutes();
            Long pauseDurationInMinutes = 0L;
            if (workLog.getPauseStart() != null && workLog.getPauseEnd() != null) {
                pauseDurationInMinutes = Duration.between(workLog.getPauseStart(), workLog.getPauseEnd()).toMinutes();
            }
            totalHours = (durationInMinutes - pauseDurationInMinutes) / 60.0;
        } else {
            logger.warn("Missing check-in or check-out for WorkLog {}", workLog.getWorkLogId());
        }
        return TotalHoursForWorKLog.builder()
                .workLogId(workLog.getWorkLogId())
                .workLogStatus(workLog.getWorkLogStatus().name())
                .totalHours(totalHours)
                .build();
    }


}
