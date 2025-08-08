package com.first.carRepairShop.entity;

import lombok.Data;


public enum WorkLogStatus {
    PLANNED,
    IN_PROGRESS,
    PAUSED,
    RESUME,
    WAITING_PARTS,
    COMPLETED,
    CANCELLED

}
