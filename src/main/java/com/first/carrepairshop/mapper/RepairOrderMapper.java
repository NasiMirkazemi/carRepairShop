package com.first.carrepairshop.mapper;

import com.first.carrepairshop.dto.RepairOrderDto;
import com.first.carrepairshop.entity.RepairOrder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RepairOrderMapper {
    RepairOrderMapper INSTANCE = Mappers.getMapper(RepairOrderMapper.class);

    RepairOrderDto toRepairOrderDto(RepairOrder repairOrder);

    RepairOrder toRepairOrderEntity(RepairOrderDto repairOrderDto);
}
