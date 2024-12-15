package com.first.carrepairshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ItemId;

    private String name;
    private String type;
    private Integer price;
    private ItemQuality qualityLevel;

    @ManyToMany(mappedBy = "items")
    private Set<RepairOrder> repairOrders=new HashSet<>();


}
