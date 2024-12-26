package com.first.carrepairshop.entity;

import com.first.carrepairshop.associations.ItemDetail;
import com.first.carrepairshop.associations.ServiceDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private LocalDate invoiceDate;
    private Integer invoiceNumber;
    private Integer carId;
    private Integer totalAmount;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "repairOrderId")
    private RepairOrder repairOrder;

    @ElementCollection
    @CollectionTable(name = "invoice_services", joinColumns = @JoinColumn(name = "invoiceId"))
    private List<ServiceDetail> servicesList=new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "invoices_items", joinColumns = @JoinColumn(name = "invoice"))
    private List<ItemDetail> itemDetailList =new ArrayList<>();
}
