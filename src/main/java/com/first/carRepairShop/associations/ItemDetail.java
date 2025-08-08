package com.first.carRepairShop.associations;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDetail {
    private Integer itemId;// refer to the item that itemDetail is associated with
    private String itemName;
    private BigDecimal itemPrice;

    @Override
    public String toString() {
        return "ItemDetail{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                '}';
    }
}
