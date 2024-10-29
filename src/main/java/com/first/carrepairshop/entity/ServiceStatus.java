package com.first.carrepairshop.entity;

import lombok.Data;


public enum ServiceStatus {
    NONE(0),
    UNDER_REPAIR(1),
    REPAIRED(2);


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    ServiceStatus(int value) {
        this.value = value;
    }

    private int value;
}
