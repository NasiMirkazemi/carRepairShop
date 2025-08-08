package com.first.carRepairShop.entity;

public enum AssignmentStatus {
    PENDING(1),
    ASSIGNED(2),      // Mechanic has been assigned but hasn't started yet
    IN_PROGRESS(3),   // Mechanic has started working
    COMPLETED(4),     // Mechanic has completed their task(s)
    REJECTED(5),
    CANCELLED(6);

    int value;

    AssignmentStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
