package com.first.carrepairshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(builderMethodName = "employeeDtoBuilder")
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeDto {
    private Integer id;
    private String name;
    private String lastname;
    private Integer age;
    private String role;
    private String phoneNumber;
    private Integer salary;

}
