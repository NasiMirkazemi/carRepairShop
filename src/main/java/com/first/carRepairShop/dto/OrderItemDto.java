package com.first.carRepairShop.dto;

import com.first.carRepairShop.entity.Item;
import com.first.carRepairShop.entity.Orders;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class OrderItemDto {
    private Integer OrderItemId;

    @NotNull(message = "Order ID cannot be null")
    @Positive(message = "Order ID must be a positive number")
    private Integer orderId;

    @NotNull(message = "Item ID cannot be null")
    @Positive(message = "Item ID must be a positive number")
    private Integer itemId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @NotNull(message = "Purchase price cannot be null")
    @DecimalMin(value = "0.01", message = "Purchase price must be at least 0.01")
    private BigDecimal purchasePrice;

}
