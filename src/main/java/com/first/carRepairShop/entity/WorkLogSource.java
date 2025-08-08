package com.first.carRepairShop.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum WorkLogSource {
    MECHANIC,
    SYSTEM;

    @JsonCreator
    public static WorkLogSource fromString(String value) {
        return WorkLogSource.valueOf(value.toUpperCase());
    }

}
