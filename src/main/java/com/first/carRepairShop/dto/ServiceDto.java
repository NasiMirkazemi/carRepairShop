package com.first.carRepairShop.dto;

import com.first.carRepairShop.entity.RepairStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ServiceDto {
    private Integer serviceId; // ID should be auto-generated, so no validation needed

    @NotBlank(message = "Service name cannot be blank")
    @Size(min = 2, max = 100, message = "Service name must be between 2 and 100 characters")
    private String serviceName;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotNull(message = "Service price cannot be null")
    @Positive(message = "Service price must be a positive number")
    private BigDecimal servicePrice;




}
