package com.first.carRepairShop.services;

import com.first.carRepairShop.dto.AssignmentDto;
import com.first.carRepairShop.dto.websocket.AssignmentDecisionMessage;
import com.first.carRepairShop.entity.AssignmentStatus;

import java.util.List;

public interface AssignmentService {
    AssignmentDto createAssignment(AssignmentDto assignmentDto);
    void processAssignmentDecision(AssignmentDecisionMessage decisionMessage);
    AssignmentDto getAssignmentById(Integer assignmentId);
    void deleteAssignmentById(Integer assignmentId);
    List<AssignmentDto> getAllAssignment();
    AssignmentDto reassignToMechanic(Integer assignmentId, Integer mechanicId);
    List<AssignmentDto> getAssignmentByMechanicId(Integer mechanicId);
    AssignmentDto updateAssignmentStatus(Integer assignmentId, AssignmentStatus status);

}
