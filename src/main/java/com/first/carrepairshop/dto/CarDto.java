package com.first.carrepairshop.dto;

import com.first.carrepairshop.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    private Integer CarId;
    private Long numberPlate;
    private String make;
    private String model;
    private Integer year;
    private Customer customer;

}
