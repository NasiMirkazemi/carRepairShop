package com.first.carrepairshop.dto;

import com.first.carrepairshop.entity.Services;
import com.first.carrepairshop.entity.RepairOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

public class MechanicDto extends EmployeeDto {
    private String specialty;
    private String certificate;
    private Integer hourlyRate;

    private Set<Services> services;

}
