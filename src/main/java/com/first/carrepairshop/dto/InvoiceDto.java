package com.first.carrepairshop.dto;

import com.first.carrepairshop.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class InvoiceDto {
    private  Integer invoiceId;
    private Integer invoiceNumber;
    private Integer carId;
    private Integer totalAmount;
    private Customer customer;
    private RepairOrder repairOrder;
    private List<Services> servicesList;
    private List<Item> items;

}
