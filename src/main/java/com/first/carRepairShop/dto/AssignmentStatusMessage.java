package com.first.carRepairShop.dto;

import com.first.carRepairShop.dto.websocket.AssignmentDecision;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentStatusMessage {
    private Integer assignmentId;
    private Integer mechanicId;
    private AssignmentDecision assignmentDecision;

}
