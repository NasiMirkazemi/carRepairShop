package com.first.carrepairshop.dto;

import com.first.carrepairshop.entity.Mechanic;
import com.first.carrepairshop.entity.RepairOrder;
import com.first.carrepairshop.entity.ServiceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ServicesDto {
    private Integer serviceId;
    private String serviceName;
    private String description;
    private Integer price;
    private Duration durationInMinutes;
    private LocalDate scheduledTime;
    private ServiceStatus serviceStatus;
    private List<Mechanic> mechanics;
    private Set<RepairOrder> repairOrders;

}
