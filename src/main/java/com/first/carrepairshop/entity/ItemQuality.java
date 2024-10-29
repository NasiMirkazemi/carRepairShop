package com.first.carrepairshop.entity;

public enum ItemQuality {
    none(0),
    lowQuality(3),
    mediumQuality (2),
    highQuality(1);
    private Integer value;

    ItemQuality(Integer value) {
        this.value = value;
    }


    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
