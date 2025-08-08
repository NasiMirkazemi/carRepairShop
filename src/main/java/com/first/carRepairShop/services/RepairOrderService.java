package com.first.carRepairShop.services;

import com.first.carRepairShop.dto.RepairOrderDto;

import java.util.List;

public interface RepairOrderService {
    RepairOrderDto addRepairOrder(RepairOrderDto repairOrderDto);

    RepairOrderDto updateRepairOrder(RepairOrderDto repairOrderDto);

    RepairOrderDto getRepairOrder(Integer repairOrderId);

    void deleteRepairOrder(Integer repairOrderId);

    RepairOrderDto trackRepairOrder(Integer repairOrderId);

    List<RepairOrderDto> getByCustomerId(Integer customerId);

    List<RepairOrderDto> getAllByCarId(Integer carId);

    List<RepairOrderDto> getAllByCarNumberPlate(String numberPlate);

}
