package com.first.carrepairshop.mapper;

import com.first.carrepairshop.dto.RepairOrderDto;
import com.first.carrepairshop.entity.RepairOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface RepairOrderMapper {
    RepairOrderMapper INSTANCE = Mappers.getMapper(RepairOrderMapper.class);

    @Mapping(target = "servicesDto", source = "services")
    RepairOrderDto toRepairOrderDto(RepairOrder repairOrder);

    @Mapping(target = "services", source = "servicesDto")
    RepairOrder toRepairOrderEntity(RepairOrderDto repairOrderDto);
}
