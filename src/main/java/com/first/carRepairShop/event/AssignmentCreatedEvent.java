package com.first.carRepairShop.event;

import com.first.carRepairShop.entity.Assignment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AssignmentCreatedEvent {
    private final Assignment assignment;
}
