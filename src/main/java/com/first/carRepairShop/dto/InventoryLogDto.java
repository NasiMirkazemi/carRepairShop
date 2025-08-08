package com.first.carRepairShop.dto;

import com.first.carRepairShop.entity.Inventory;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryLogDto {
    private Long id;

    private Integer inventoryId;
    private String itemName;      // e.g., "Brake Pad"
    private String itemCategory;  // e.g., "Parts"

    private int quantityChanged;

    private String direction; // "IN" or "OUT"

    private String reason;

    private LocalDateTime timestamp;

}
