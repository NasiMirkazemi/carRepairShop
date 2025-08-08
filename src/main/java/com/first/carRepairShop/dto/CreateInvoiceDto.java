package com.first.carRepairShop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateInvoiceDto {
    private Integer repairOrderId;
    private BigDecimal discountRate;
    private String createdBy;

}
