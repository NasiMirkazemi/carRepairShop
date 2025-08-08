package com.first.carRepairShop.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MechanicRegister {
    private Integer id;
    @NotNull(message = "User data is required")
    @Valid
    private UserDto user;
    @NotNull(message = "Age is required")
    private Integer age;
    private Integer salary;
    @NotBlank(message = "Department is required")
    private String department;
    private String specialty;
    private String certificate;

    @NotNull(message = "Hourly rate cannot be null")
    @Positive(message = "Hourly rate must be a positive number")
    private Integer hourlyRate;

}
