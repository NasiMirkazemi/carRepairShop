package com.first.carRepairShop.entity;

import com.first.carRepairShop.associations.ItemDetail;
import com.first.carRepairShop.associations.ServiceDetail;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Column(name = "invoice_number", unique = true, nullable = false)
    private String invoiceNumber;

    private Integer carId;
    private String numberPlate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer-id", nullable = false)
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repair_order_id", unique = true, nullable = false)
    private RepairOrder repairOrder; //  Each invoice is for ONE repair orders.

    @ElementCollection
    @CollectionTable(name = "invoice_services", joinColumns = @JoinColumn(name = "invoice_id"))
    private List<ServiceDetail> servicesDetailList = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "invoice_items", joinColumns = @JoinColumn(name = "invoice_id"))
    private List<ItemDetail> itemsDetailList = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime lastUpdated;
    private BigDecimal serviceAmount;
    private BigDecimal itemAmount;
    private BigDecimal taxRate;
    private BigDecimal discountRate;
    private BigDecimal totalAmount;
    private String createdBy;
    private String finalizedBy;
    private LocalDateTime finalizedAt;
    private LocalDateTime cancelAt;

    @OneToMany(mappedBy = "originalInvoice")
    private List<CreditNote> creditNotes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvoiceStatus invoiceStatus = InvoiceStatus.DRAFT; // default

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


