package com.first.carRepairShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String creditNoteNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "invoice_id")
    private Invoice originalInvoice;

    private LocalDateTime issuedDate;
    private BigDecimal amount;

    private String reason;

    @PrePersist
    public void prePersist() {
        this.issuedDate = LocalDateTime.now();
    }


}
