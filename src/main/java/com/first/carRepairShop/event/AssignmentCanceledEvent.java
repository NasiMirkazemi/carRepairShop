package com.first.carRepairShop.event;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Builder
@Getter
public class AssignmentCanceledEvent {
    private final Integer assignmentId;
    private final Integer mechanicId;
    private final String username;
    private final Integer repairOrderId;
    private final String reason;
    private final LocalDateTime cancelledAt;

}
