package com.first.carRepairShop.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class InventoryDto {
    private Integer inventoryId;
    @NotNull(message = "Item ID cannot be null")
    @Min(value = 1, message = "Item ID must be greater than 0")
    private Integer itemId;
    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;
    @Min(value = 0, message = "Minimum stock level cannot be negative")
    private int minimumStockLevel;
    @Min(value = 0, message = "Maximum stock level cannot be negative")
    private Integer maximumStockLevel;
    @NotBlank(message = "location can not be null")
    private String location;
    @NotBlank(message="Supplier cannot be null")
    private String supplier;
    @DecimalMin(value = "0.0", inclusive = false, message = "Purchase price must be positive")
    private BigDecimal purchasePrice;
    @DecimalMin(value = "0.0", inclusive = false, message = "Selling price must be positive")
    private BigDecimal sellingPrice;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    private String note;

    @Override
    public String toString() {
        return "InventoryDto{" +
                "inventoryId=" + inventoryId +
                ", itemId=" + itemId +
                ", quantity=" + quantity +
                ", minimumStockLevel=" + minimumStockLevel +
                ", maximumStockLevel=" + maximumStockLevel +
                ", location='" + location + '\'' +
                ", supplier='" + supplier + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", sellingPrice=" + sellingPrice +
                ", createdAt=" + createdAt +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
