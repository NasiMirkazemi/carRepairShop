package com.first.carRepairShop.entity;

public enum PaymentStatus {
    FAILED(0),
    PENDING(1),
    COMPLETED(2),
    REFUNDED(3),
    CANCELED(4);
    private int value;

    PaymentStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
