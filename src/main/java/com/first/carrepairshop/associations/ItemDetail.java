package com.first.carrepairshop.associations;

import jakarta.persistence.Embeddable;
import lombok.Data;
import org.springframework.stereotype.Component;

@Embeddable

public class ItemDetail {
    private Integer itemId;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Integer itemPrice) {
        this.itemPrice = itemPrice;
    }

    private String itemName;
    private Integer itemPrice;
}
