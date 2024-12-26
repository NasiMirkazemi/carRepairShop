package com.first.carrepairshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CustomerDto {
    private List<CarDto> cars;
    private Integer customerId;
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private List<InvoiceDto> invoiceDtos;

}
