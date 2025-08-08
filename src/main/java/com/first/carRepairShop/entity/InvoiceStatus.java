package com.first.carRepairShop.entity;

public enum InvoiceStatus {
    DRAFT(0),
    FINALIZED(1),
    ISSUED(2),
    PAID(3),
    CANCELLED(4),
    REFUNDED(5),
    OVERDUE(6);


    int value;

    InvoiceStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
