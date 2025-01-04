package com.first.carrepairshop.entity;

import com.first.carrepairshop.associations.ItemDetail;
import com.first.carrepairshop.associations.ServiceDetail;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invoiceId;
    private LocalDate invoiceDate;
    private Integer invoiceNumber;
    private Integer carId;
    private Integer totalAmount;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "repair_order_id")
    private RepairOrder repairOrder;

    @ElementCollection
    @CollectionTable(name = "invoice_services", joinColumns = @JoinColumn(name = "invoiceId"))
    private List<ServiceDetail> servicesDetailList = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "invoices_items", joinColumns = @JoinColumn(name = "invoice"))
    private List<ItemDetail> itemsDetailList = new ArrayList<>();

}
