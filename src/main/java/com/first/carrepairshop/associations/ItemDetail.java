package com.first.carrepairshop.associations;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ItemDetail {
    private Integer itemId;
    private String itemName;
    private Integer itemPrice;
}
