package com.first.carRepairShop.dto.websocket;

public enum AssignmentDecision {
    ACCEPTED(1),
    REJECTED(0);
    private int value;

    AssignmentDecision(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
