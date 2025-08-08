package com.first.carRepairShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate startDate;
    private LocalDate endDate;

    private Double totalHoursWorked;
    private BigDecimal hourlyRate;

    private BigDecimal grossPay;   // totalHoursWorked * hourlyRate

    private BigDecimal deductions;

    private BigDecimal netPay;
    @ManyToOne
    @JoinColumn(name = "mechanic_id", nullable = false)
    private Mechanic mechanic;

    private LocalDateTime issuedAt;

    @Enumerated(EnumType.STRING)
    private PayrollStatus status;
    private LocalDateTime approvedAt;
    @ManyToOne(optional = true)
    private UserApp approvedBy;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserApp user;
}
