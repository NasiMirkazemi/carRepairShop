package com.first.carRepairShop.entity;

public enum AppointmentStatus {
    REQUESTED(1),
    CONFIRMED(2),
    COMPLETED(3),
    CANCELED(0);

    private int Value;

    AppointmentStatus(int value) {
        Value = value;
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int value) {
        Value = value;
    }

}
