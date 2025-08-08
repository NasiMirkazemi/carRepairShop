package com.first.carRepairShop.event;

import com.first.carRepairShop.dto.websocket.AssignmentDecision;
import com.first.carRepairShop.dto.websocket.AssignmentNewMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignmentDecisionEvent {
    private AssignmentNewMessage message;
    private  AssignmentDecision decision;
    private  String note;

}
