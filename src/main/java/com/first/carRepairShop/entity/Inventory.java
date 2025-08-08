package com.first.carRepairShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inventoryId;

    @OneToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int minimumStockLevel;

    private Integer maximumStockLevel; // Optional

    private String storageLocation;// Where the item is stored

    private String supplier; // Supplier name

    @Column(precision = 10, scale = 2)
    private BigDecimal purchasePrice;

    @Column(precision = 10, scale = 2)
    private BigDecimal sellingPrice;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime lastUpdated;
    private String note;
    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InventoryLog> logs = new ArrayList<>();
    private boolean deleted = false;


    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();  // Set only when inserting
        this.lastUpdated = LocalDateTime.now(); // Set when inserting
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdated = LocalDateTime.now();

    }
}
