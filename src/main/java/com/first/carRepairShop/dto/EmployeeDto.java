package com.first.carRepairShop.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "employee_type", discriminatorType = DiscriminatorType.STRING)
@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeDto {
    @NotNull(message = "User data is required")
    @Valid
    private UserDto user;
    private Integer age;
    private LocalDate hireDate;
    private Integer salary;
    private String department;

}
