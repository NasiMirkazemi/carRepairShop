package com.first.carRepairShop.dto.websocket;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentCanceledMessage {
    private  Integer assignmentId;
    private  Integer mechanicId;
    private String username;
    private  Integer repairOrderId;
    private  String reason;
    private  LocalDateTime cancelledAt;

}
