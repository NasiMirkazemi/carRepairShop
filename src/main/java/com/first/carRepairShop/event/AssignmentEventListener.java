package com.first.carRepairShop.event;

import com.first.carRepairShop.entity.Assignment;
import com.first.carRepairShop.services.AssignmentNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class AssignmentEventListener {
    private final AssignmentNotificationService notificationService;


}
