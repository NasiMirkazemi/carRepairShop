package com.first.carRepairShop.mapper;

import com.first.carRepairShop.dto.RepairOrderDto;
import com.first.carRepairShop.dto.RepairOrderDtoFull;
import com.first.carRepairShop.entity.RepairOrder;
import com.first.carRepairShop.entity.RepairStatus;
import com.first.carRepairShop.entity.WorkLogStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ServiceMapper.class, ServiceDetailMapper.class, ItemDetailMapper.class, CustomerMapper.class, CarMapper.class})
public interface RepairOrderMapper {
    RepairOrderMapper INSTANCE = Mappers.getMapper(RepairOrderMapper.class);

    @Named("enumToString")
    static String mapWorkLogStatusToString(RepairStatus repairStatus) {
        return repairStatus != null ? repairStatus.name() : null;
    }

    @Named("StringToEnum")
    static RepairStatus mapStringToStatus(String repairStatus) {
        return repairStatus != null ? RepairStatus.valueOf(repairStatus.toUpperCase()) : null;

    }

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "carId", source = "car.carId")
    @Mapping(target = "plannedServices", source = "plannedServices", qualifiedByName = "mapServicesToServiceDetailDtoList")
    @Mapping(target = "plannedItems", source = "plannedItems")
    @Mapping(source = "repairStatus", target = "repairStatus", qualifiedByName = "enumToString")
    RepairOrderDto toDto(RepairOrder repairOrder);

    @Mapping(target = "customer", ignore = true) // Customer is handled in the service layer
    @Mapping(target = "car", ignore = true)      // Car is handled in the service layer
    @Mapping(target = "plannedServices", source = "plannedServices ")
    @Mapping(target = "plannedItems", source = "plannedItems")
    @Mapping(source = "repairStatus", target = "repairStatus", qualifiedByName = "StringToEnum")
    RepairOrder toEntityFromDto(RepairOrderDto repairOrderDto);

    @Mapping(target = "customer", source = "customer")
    @Mapping(target = "car", source = "car")
    @Mapping(target = "plannedServices ", source = "plannedServices ")
    @Mapping(target = "plannedItems", source = "plannedItems")
    RepairOrderDtoFull toRepairOrderDtoFull(RepairOrder repairOrder);

    @Mapping(target = "customer", source = "customer")
    @Mapping(target = "car", source = "car")
    @Mapping(target = "plannedServices ", source = "plannedServices ")
    @Mapping(target = "plannedItems", source = "plannedItems")
    RepairOrder toRepairOrderEntityFromFull(RepairOrderDtoFull repairOrderDtoFull);


}
