package com.first.carRepairShop.entity;


public enum RepairStatus {
    NEW(1),
    WAITING_FOR_ASSIGNMENT(2),
    IN_PROGRESS(3),
    WAITING_FOR_PARTS(4),

    COMPLETED(5),
    CANCELLED(0);


    int value;

    RepairStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
