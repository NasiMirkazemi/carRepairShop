package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.associations.ItemDetail;
import com.first.carRepairShop.associations.ServiceDetail;
import com.first.carRepairShop.dto.websocket.AssignmentCanceledMessage;
import com.first.carRepairShop.entity.Assignment;
import com.first.carRepairShop.entity.Mechanic;
import com.first.carRepairShop.event.AssignmentCanceledEvent;
import com.first.carRepairShop.event.AssignmentEventPublisher;
import com.first.carRepairShop.dto.websocket.AssignmentNewMessage;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.repository.MechanicRepository;
import com.first.carRepairShop.services.AssignmentNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssignmentNotificationServiceImpl implements AssignmentNotificationService {
    private final MechanicRepository mechanicRepository;
    private final AssignmentEventPublisher assignmentEventPublisher;

    @Override
    public void notifyMechanicNewAssignment(Assignment assignment) {
        if (assignment == null) {
            throw new BadRequestException("Invalid assignment ");
        }
        Mechanic mechanic = mechanicRepository.findById(assignment.getMechanicId())
                .orElseThrow(() -> new NotFoundException("No Mechanic found id :" + assignment.getMechanicId()));
        AssignmentNewMessage assignmentNewMessage = AssignmentNewMessage.builder()
                .assignmentId(assignment.getAssignmentId())
                .repairOrderId(assignment.getRepairOrder().getRepairOrderId())
                .mechanicId(assignment.getMechanicId())
                .username(mechanic.getUsername())
                .status(assignment.getAssignmentStatus().name())
                .startDate(assignment.getStartDate())
                .endDate(assignment.getEndDate())
                .plannedServices(assignment.getRepairOrder().getPlannedServices().stream()
                        .map(ServiceDetail::getServiceName)
                        .collect(Collectors.toList()))
                .plannedItems(assignment.getRepairOrder().getPlannedItems().stream()
                        .map(ItemDetail::getItemName)
                        .collect(Collectors.toList()))
                .build();
        log.info("on send Message");
        assignmentEventPublisher.sendNewAssignment(assignmentNewMessage);


    }

    public void notifyMechanicCanceledAssignment(AssignmentCanceledEvent event) {
        if (event == null) throw new BadRequestException("Event can not be null");
        Mechanic mechanic = mechanicRepository.findById(event.getMechanicId())
                .orElseThrow(() -> new NotFoundException("No Mechanic found with id: " + event.getMechanicId()));
        AssignmentCanceledMessage canceledMessage = AssignmentCanceledMessage.builder()
                .assignmentId(event.getAssignmentId())
                .repairOrderId(event.getRepairOrderId())
                .mechanicId(mechanic.getId())
                .username(mechanic.getUsername())
                .cancelledAt(event.getCancelledAt())
                .reason(event.getReason())
                .build();
        log.info("on send Message");
        assignmentEventPublisher.sendCanceledAssignment(canceledMessage);

    }
}
