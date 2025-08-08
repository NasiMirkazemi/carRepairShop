package com.first.carRepairShop.dto;

import com.first.carRepairShop.entity.Assignment;
import com.first.carRepairShop.entity.Customer;
import com.first.carRepairShop.entity.RepairOrder;
import com.first.carRepairShop.entity.WorkLog;
import lombok.*;

import java.util.List;

@Data
@RequiredArgsConstructor
public class InvoiceData {
    private final RepairOrder repairOrder;
    private final Assignment assignment;
    private final List<WorkLog> workLogList;

}
