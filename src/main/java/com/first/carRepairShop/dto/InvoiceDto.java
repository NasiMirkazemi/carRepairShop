package com.first.carRepairShop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.first.carRepairShop.entity.CreditNote;
import com.first.carRepairShop.entity.InvoiceStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class InvoiceDto {

    private Integer invoiceId;

    @NotNull(message = "Invoice date cannot be null")
    private LocalDate invoiceDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String invoiceNumber;

    @NotNull(message = "Car ID cannot be null")
    @Positive(message = "Car ID must be a positive number")
    private Integer carId;

    @NotNull(message = "Car number plate cannot be null")
    private String carNumberPlate;

    @NotNull(message = "Customer id cannot be null")
    @Positive(message = "Customer id must be positive number")
    private Integer customerId;

    @NotNull(message = "repairOrder ID cannot be null")
    @Positive(message = "Repair orders ID must be a positive number")
    private Integer repairOrderId;

    @NotNull
    @Size(min = 1, message = "At least one service detail must be provided")
    @Valid
    private List<ServiceDetailDto> servicesDetailDtoList = new ArrayList<>();
    private String invoiceStatus;

    @NotNull
    @Valid
    private List<ItemDetailDto> itemDetailDtoList = new ArrayList<>();
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal serviceAmount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal itemAmount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal taxRate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal discountRate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal totalAmount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<CreditNote> creditNotes = new ArrayList<>();

    @NotNull(message = "created by cannot be null")
    private String createdBy;
    @NotNull(message = "finalized by cannot be null")
    private String finalizedBy;
    private LocalDateTime finalizedAt;
    private LocalDateTime cancelAt;




    @Override
    public String toString() {
        return "InvoiceDto{" +
                "invoiceId=" + invoiceId +
                ", invoiceDate=" + invoiceDate +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", carId=" + carId +
                ", carNumberPlate='" + carNumberPlate + '\'' +
                ", customerId=" + customerId +
                ", repairOrderId=" + repairOrderId +
                ", servicesDetailDtoList=" + servicesDetailDtoList +
                ", itemDetailDtoList=" + itemDetailDtoList +
                ", totalAmount=" + totalAmount +
                '}';
    }
}