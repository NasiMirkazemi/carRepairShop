package com.first.carRepairShop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.first.carRepairShop.entity.PaymentMethod;
import com.first.carRepairShop.entity.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PaymentDto {
    private Integer paymentId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String paymentNumber;
    @NotNull(message = "Invoice ID is required")
    @Positive(message = "Invoice ID must be a positive number")
    private Integer invoiceId;
    private String invoiceNumber;
    private Integer customerId;

    @NotNull(message = "Payment amount is required")
    @DecimalMin(value = "0.01", message = "Payment amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Payment date is required")
    private LocalDate paymentDate;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    @NotNull(message = "Payment status is required")
    private PaymentStatus paymentStatus;

    @Size(max = 255, message = "Notes cannot exceed 255 characters")
    private String notes;

}
