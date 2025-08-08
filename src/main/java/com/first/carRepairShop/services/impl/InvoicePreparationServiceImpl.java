package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.dto.InvoiceData;
import com.first.carRepairShop.entity.*;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.BusinessConflictException;
import com.first.carRepairShop.exception.BusinessException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.repository.RepairOrderRepository;
import com.first.carRepairShop.services.InvoicePreparationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoicePreparationServiceImpl implements InvoicePreparationService {
    private final RepairOrderRepository repairOrderRepository;

    public InvoiceData prepareInvoiceData(Integer repairOrderId) {
        if (repairOrderId == null) throw new BadRequestException("Repair order is required");
        RepairOrder repairOrder = repairOrderRepository.findById(repairOrderId)
                .orElseThrow(() -> new NotFoundException("No repair order found with id: " + repairOrderId));
        Assignment assignment = repairOrder.getAssignment();
        if (assignment == null || !assignment.getAssignmentStatus().equals(AssignmentStatus.COMPLETED)) {
            throw new BusinessConflictException("Assignment is required and must be Completed to generate invoice");
        }
        List<WorkLog> workLogList = assignment.getWorkLogs();
        if (workLogList == null || workLogList.isEmpty()) {
            throw new BusinessConflictException("Work logs are required to generate invoice");
        }
        List<Integer> noneCompleteId = workLogList.stream()
                .filter(workLog -> !workLog.getWorkLogStatus().equals(WorkLogStatus.COMPLETED))
                .map(workLog -> workLog.getWorkLogId())
                .collect(Collectors.toList());
        if (noneCompleteId != null || !noneCompleteId.isEmpty()) {
            noneCompleteId.forEach(id -> System.out.println("Incomplete work log id: " + id));
            throw new BusinessConflictException("All work log status must be COMPLETED");
        }
        return new InvoiceData(repairOrder, assignment, workLogList);
    }

}
