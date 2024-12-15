package com.first.carrepairshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeDto {
    private Integer employeeId;
    private String name;
    private String lastname;
    private Integer age;
    private String role;
    private String phoneNumber;
    private Integer salary;

}
