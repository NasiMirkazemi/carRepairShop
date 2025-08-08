package com.first.carRepairShop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.first.carRepairShop.entity.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class OrdersDto {
    private Integer id; // Nullable for new orders, required for updates

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String orderNumber;

    @NotNull(message = "Supplier name is required")
    @Size(min = 2, max = 100, message = "Supplier name must be between 2 and 100 characters")
    private String supplier;

    @NotNull(message = "Orders date is required")
    private LocalDateTime orderDate;

    @NotNull(message = "Orders status is required")
    private OrderStatus status;

    @NotNull(message = "created by id is required")
    private Integer createdBy;
    private Integer receivedBy;
    private LocalDateTime receivedDate;
    private String receivingComment;


    @NotNull(message = "Orders items cannot be null")
    @Size(min = 1, message = "At least one orders item is required")
    @Valid  // This will trigger validation on each item in the orderItems list
    private List<OrderItemDto> orderItems; // List of ordered items

}
