package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.dto.AssignmentDto;
import com.first.carRepairShop.dto.AssignmentStatusMessage;
import com.first.carRepairShop.dto.websocket.AssignmentDecision;
import com.first.carRepairShop.dto.websocket.AssignmentDecisionMessage;
import com.first.carRepairShop.entity.*;
import com.first.carRepairShop.event.AssignmentCanceledEvent;
import com.first.carRepairShop.event.AssignmentCreatedEvent;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.mapper.AssignmentMapper;
import com.first.carRepairShop.mapper.WorkLogMapper;
import com.first.carRepairShop.repository.AssignmentRepository;
import com.first.carRepairShop.repository.MechanicRepository;
import com.first.carRepairShop.repository.RepairOrderRepository;
import com.first.carRepairShop.repository.WorkLogRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl {
    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper;
    private final RepairOrderRepository repairOrderRepository;
    private final MechanicRepository mechanicRepository;
    private final WorkLogMapper workLogMapper;
    private final WorkLogRepository workLogRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final ApplicationEventPublisher eventPublisher;

    private final Logger logger = LoggerFactory.getLogger(AssignmentServiceImpl.class);

    @Transactional
    public AssignmentDto createAssignment(AssignmentDto assignmentDto) {
        if (assignmentDto == null)
            throw new BadRequestException("Assignment is required");
        RepairOrder repairOrder = repairOrderRepository.findById(assignmentDto.getRepairOrderId())
                .orElseThrow(() -> new NotFoundException("No RepairOrder found for Id:" + assignmentDto.getRepairOrderId()));
        Assignment assignment = assignmentMapper.toEntity(assignmentDto);

        if (assignment.getWorkLogs() != null && !assignment.getWorkLogs().isEmpty()) {
            List<WorkLog> workLogList = workLogMapper.toEntityList(assignmentDto.getWorkLogs());
            workLogList.stream()
                    .filter(workLog -> workLog.getMechanic() == null)
                    .findFirst()
                    .ifPresent(workLog -> {
                        new BadRequestException("Mechanic ID is required for all WorkLogs");
                    });
            for (WorkLog workLog : workLogList) {
                workLog.setAssignment(assignment);
            }
            assignment.setWorkLogs(workLogList);
        }
        Mechanic mechanic = mechanicRepository.findById(assignmentDto.getMechanicId())
                .orElseThrow(() -> new NotFoundException("No Mechanic found with id: " + assignmentDto.getMechanicId()));
        assignment.setMechanicId(mechanic.getId());
        assignment.setRepairOrder(repairOrder);
        assignment.setMechanicId(assignmentDto.getMechanicId());
        assignment.setAssignmentStatus(AssignmentStatus.PENDING);
        assignment.setStartDate(LocalDate.now());
        Assignment saved = assignmentRepository.save(assignment);

        eventPublisher.publishEvent(new AssignmentCreatedEvent(saved));
        return assignmentMapper.toDto(saved);
    }


    public void processAssignmentDecision(AssignmentDecisionMessage decisionMessage) {
        Assignment assignment = assignmentRepository.findById(decisionMessage.getAssignmentId())
                .orElseThrow(() -> new NotFoundException("No assignment found whit id :"
                        + decisionMessage.getAssignmentId()));
        if (decisionMessage.getAssignmentDecision().equals(AssignmentDecision.ACCEPTED)) {
            assignment.setAssignmentStatus(AssignmentStatus.ASSIGNED);
        } else if (decisionMessage.getAssignmentDecision().equals(AssignmentDecision.REJECTED)) {
            assignment.setAssignmentStatus(AssignmentStatus.REJECTED);
        }
        assignmentRepository.save(assignment);
        messagingTemplate.convertAndSend("/topic/assignment-status", new AssignmentStatusMessage());
        //ToDo
        //after change assignment status we should notify the office
    }

    public AssignmentDto getAssignmentById(Integer assignmentId) {
        if (assignmentId == null)
            throw new BadRequestException("Assignment Id is required");
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new NotFoundException("No Assignment found whit id:" + assignmentId));

        return assignmentMapper.toDto(assignment);
    }

    public void deleteAssignmentById(Integer assignmentId) {
        if (assignmentId == null)
            throw new BadRequestException(" Assignment Id is required");
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new NotFoundException("No Assignment found with id :" + assignmentId));
        if (assignment.getAssignmentStatus() != AssignmentStatus.ASSIGNED
                && assignment.getAssignmentStatus() != AssignmentStatus.PENDING
                && assignment.getAssignmentStatus() != AssignmentStatus.CANCELLED) {
            throw new BadRequestException("Only assignments with status ASSIGNED or CANCELLED can be deleted.");
        }
        RepairOrder repairOrder = assignment.getRepairOrder();
        if (repairOrder != null) {
            repairOrder.setAssignment(null);
        }
        assignment.setRepairOrder(null);
        assignmentRepository.delete(assignment);
        logger.info("Assignment with id {} has been deleted", assignment.getAssignmentId());
    }

    public List<AssignmentDto> getAllAssignment() {
        List<Assignment> assignmentList = assignmentRepository.findAll();
        if (assignmentList.isEmpty()) {
            logger.info("NO Assignment Exist!!");
        }
        return assignmentMapper.toDtoList(assignmentList);
    }

    @Transactional
    public AssignmentDto reassignToMechanic(Integer assignmentId, Integer mechanicId) {
        if (assignmentId == null || mechanicId == null)
            throw new BadRequestException("Assignment id and Mechanic id are required");
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new NotFoundException("No Assignment found with id :" + assignmentId));
        Mechanic mechanic = mechanicRepository.findById(mechanicId)
                .orElseThrow(() -> new NotFoundException("No Mechanic found with id :" + mechanicId));
        assignment.setMechanicId(mechanic.getId());
        Assignment saved = assignmentRepository.save(assignment);

        eventPublisher.publishEvent(new AssignmentCreatedEvent(saved));
        return assignmentMapper.toDto(assignment);
    }

    public List<AssignmentDto> getAssignmentByMechanicId(Integer mechanicId) {
        if (mechanicId == null)
            throw new BadRequestException("Mechanic Id is required");
        List<Assignment> assignmentList = assignmentRepository.findAssignmentByMechanicId(mechanicId);
        if (assignmentList != null && !assignmentList.isEmpty()) {
            return assignmentList.stream()
                    .map(assignmentMapper::toDto)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public AssignmentDto updateAssignmentStatus(Integer assignmentId, AssignmentStatus status) {
        if (assignmentId == null || status == null)
            throw new BadRequestException("Assignment id and Status is required");
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new NotFoundException("No Assignment found with id :" + assignmentId));
        assignment.setAssignmentStatus(status);
        Assignment savedAssignment = assignmentRepository.save(assignment);
        return assignmentMapper.toDto(savedAssignment);
    }

    public AssignmentDto cancelAssignment(Integer assignmentId, String reason) {
        if (assignmentId == null)
            throw new BadRequestException("Assignment id is required");
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new NotFoundException("No Assignment found with id :" + assignmentId));
        if (assignment.getAssignmentStatus().equals(AssignmentStatus.COMPLETED)
                || assignment.getAssignmentStatus().equals(AssignmentStatus.IN_PROGRESS)) {
            throw new BadRequestException("Cannot cancel a COMPLETED  or IN_PROGRESS assignment");
        }
        if (assignment.getAssignmentStatus().equals(AssignmentStatus.CANCELLED)) {
            throw new BadRequestException("Assignment is already CANCELLED");
        }
        Mechanic mechanic = mechanicRepository.findById(assignment.getMechanicId())
                .orElseThrow(() -> new NotFoundException("No Mechanic found with id: " + assignment.getMechanicId()));
        assignment.setAssignmentStatus(AssignmentStatus.CANCELLED);

        eventPublisher.publishEvent(new AssignmentCanceledEvent(assignment.getAssignmentId()
                , mechanic.getId()
                , mechanic.getUsername()
                , assignment.getRepairOrder().getRepairOrderId()
                , reason
                , LocalDateTime.now())
        );

        assignment.setMechanicId(null);
        assignment.getRepairOrder().setRepairStatus(RepairStatus.WAITING_FOR_ASSIGNMENT);
        Assignment savedAssignment = assignmentRepository.save(assignment);
        return assignmentMapper.toDto(savedAssignment);
    }
}
