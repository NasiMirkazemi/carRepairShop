package com.first.carRepairShop.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignmentNewMessage {
    private Integer assignmentId;
    private Integer repairOrderId;
    private Integer mechanicId;
    private String username;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> plannedServices;// Descriptions of planned services
    private List<String> plannedItems;
    private String message;

}
