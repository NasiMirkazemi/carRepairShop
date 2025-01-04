package com.first.carrepairshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class InvoiceDto {
    private Integer invoiceId;
    private LocalDate invoiceDate;
    private Integer invoiceNumber;
    private Integer carId;
    private Integer totalAmount;
    private CustomerDto customerDto;
    private RepairOrderDto repairOrderDto;
    private List<ServiceDetailDto> servicesDetailList;
    private List<ItemDetailDto> itemsDetailList;


}
