package com.first.carrepairshop.dto;

import com.first.carrepairshop.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepairOrderDto {
    private Integer orderId;
    private String description;
    private LocalDate serviceDate;
    private Integer customerId;
    private Integer MechanicId;
    private Set<Services> services;
    private Set<Item> items;

}
