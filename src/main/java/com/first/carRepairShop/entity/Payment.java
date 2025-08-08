package com.first.carRepairShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;
    @Column(unique = true, nullable = false)
    private String paymentNumber;
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
    private String invoiceNumber;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    private BigDecimal amount;

    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private String notes;


}
