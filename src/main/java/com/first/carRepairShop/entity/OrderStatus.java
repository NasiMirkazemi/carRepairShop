package com.first.carRepairShop.entity;

public enum OrderStatus {
    PENDING(1),    // Orders placed, waiting for processing
    SHIPPED(2),    // Supplier has shipped the orders
    RECEIVED(3),   // Orders has been received and added to inventory
    CANCELED  (0) ;
    int value;// Orders was canceled

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    OrderStatus(int value) {
        this.value = value;
    }

    OrderStatus() {
    }
}
