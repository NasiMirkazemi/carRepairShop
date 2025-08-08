package com.first.carRepairShop.dto;

import com.first.carRepairShop.entity.PayrollStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayrollDto {
    private Integer id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double totalHoursWorked;
    private BigDecimal hourlyRate;
    private BigDecimal grossPay;
    private BigDecimal deductions;
    private BigDecimal netPay;
    private LocalDateTime issuedAt;
    private String status;

    private String mechanicName;
    private Integer mechanicId;

    private String approvedByName;
    private Integer approvedById;
    private LocalDateTime approvedAt;

}
