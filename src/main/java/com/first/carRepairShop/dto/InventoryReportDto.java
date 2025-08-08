package com.first.carRepairShop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryReportDto {
    private Integer inventoryId;
    private String itemName;
    private String itemCategory;
    private int quantity;
    private int quantityChanged;
    private String direction; // IN, OUT, etc.
    private String reason;
    private int minimumStockLevel;
    private Integer maximumStockLevel;
    private String stockStatus; // OK, LOW, EXCEEDED
    private String storageLocation;
    private String supplier;
    private BigDecimal purchasePrice;
    private BigDecimal sellingPrice;
    private LocalDateTime lastUpdated;
    private String note;

}
