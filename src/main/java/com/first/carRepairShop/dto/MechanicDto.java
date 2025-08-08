package com.first.carRepairShop.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MechanicDto {

    @NotNull(message = "User data is required")
    @Valid
    private UserDto user;
    @NotNull(message = "Age is required")
    private Integer age;
    private Integer salary;
    private String department;
    private String specialty;
    private String certificate;

    @NotNull(message = "Hourly rate cannot be null")
    @Positive(message = "Hourly rate must be a positive number")
    private BigDecimal hourlyRate;

    @Valid
    private List<WorkLogDto> workLogs = new ArrayList<>();

}
