package com.first.carRepairShop.services;

import com.first.carRepairShop.entity.Assignment;
import com.first.carRepairShop.entity.Inventory;
import com.first.carRepairShop.event.AssignmentCanceledEvent;

public interface AssignmentNotificationService {
    public void notifyMechanicNewAssignment( Assignment assignment);
    void notifyMechanicCanceledAssignment(AssignmentCanceledEvent event);
}
