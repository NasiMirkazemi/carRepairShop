package com.first.carRepairShop.dto;

import com.first.carRepairShop.associations.ServiceDetail;
import com.first.carRepairShop.entity.RepairStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrackRepairOrderDto {

    private String repairOrderNumber;
    private String customerName;
    private Integer carPlate;
    private RepairStatus repairStatus;
    private List<ServiceDetailDto> plannedServices;
    private List<ItemDetailDto> plannedItems;
    private List<ServiceDetailDto> performedService;
    private List<ItemDetailDto> usedItems;
}
