package com.first.carrepairshop.dto;

import com.first.carrepairshop.entity.Services;
import com.first.carrepairshop.entity.RepairOrder;
import jakarta.persistence.DiscriminatorValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor

//@DiscriminatorValue("mechanic")

public class MechanicDto extends EmployeeDto {
    private String specialty;
    private String certificate;
    private Integer hourlyRate;

    private List<ServicesDto> servicesDto = new ArrayList<>();

}
