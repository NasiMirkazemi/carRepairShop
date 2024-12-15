package com.first.carrepairshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RepairOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer repairOrderId;
    private String description;
    private LocalDate serviceDate;
    private Integer customerId;
    private Integer mechanicId;


    @ManyToMany
    @JoinTable(name = "repairOrder-service",
    joinColumns = @JoinColumn(name = "orderId"),
    inverseJoinColumns = @JoinColumn(name = "serviceId"))
    private Set<Services> services = new HashSet<>();


    @ManyToMany
    @JoinTable(name="repairOrder-item",
    joinColumns = @JoinColumn(name="orderId"),
    inverseJoinColumns = @JoinColumn(name="itemId"))
    private Set<Item> items =new HashSet<>();

}
