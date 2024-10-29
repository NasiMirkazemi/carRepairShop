package com.first.carrepairshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invoiceId;
    private Integer invoiceNumber;
    private Integer carId;
    private Integer totalAmount;

   @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;



    @OneToOne
    @JoinColumn(name = "orderId")
    private RepairOrder repairOrder;

    @OneToMany
    @JoinColumn(name="invoiceId")
    private List<Services> servicesList;

    @OneToMany
    @JoinColumn(name="invoiceId")
    private List<Item> items;
}
