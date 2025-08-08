package com.first.carRepairShop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ItemDetailDto {
    private Integer itemId;
    private String itemName;
    private BigDecimal itemPrice;

    @Override
    public String toString() {
        return "ItemDetailDto{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                '}';
    }
}
