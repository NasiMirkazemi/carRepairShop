package com.first.carrepairshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CarDto {
    private Integer carId;
    private Long numberPlate;
    private String make;
    private String model;
    private Integer year;
    private CustomerDto customerDto;

}
