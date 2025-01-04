package com.first.carrepairshop.dto;

import com.first.carrepairshop.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class RepairOrderDto {
    private Integer repairOrderId;
    private String description;
    private LocalDate serviceDate;
    private Integer customerId;
    private Integer mechanicId;
    private List<ServicesDto> servicesDto = new ArrayList<>();


}
