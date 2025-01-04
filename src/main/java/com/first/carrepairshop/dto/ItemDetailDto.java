package com.first.carrepairshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ItemDetailDto {
    private Integer itemId;
    private String itemName;
    private Integer itemPrice;

}
