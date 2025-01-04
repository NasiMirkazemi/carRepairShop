package com.first.carrepairshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor


public class CustomerDto {
    private Integer customerId;
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private List<CarDto> carsDto;
    private List<InvoiceDto> invoicesDto;

}
