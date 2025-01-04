package com.first.carrepairshop.dto;

import com.first.carrepairshop.entity.ItemQuality;
import com.first.carrepairshop.entity.RepairOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class ItemDto {
    private Integer itemId;
    private String name;
    private String type;
    private Integer price;
    private ItemQuality qualityLevel;


}
