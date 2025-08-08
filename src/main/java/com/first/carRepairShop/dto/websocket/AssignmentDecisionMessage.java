package com.first.carRepairShop.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AssignmentDecisionMessage {
    private Integer assignmentId;
    private Integer mechanicId;
    private AssignmentDecision assignmentDecision;
    private String note;
}
