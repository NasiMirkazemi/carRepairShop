package com.first.carrepairshop.mapper;

import com.first.carrepairshop.dto.MechanicDto;
import com.first.carrepairshop.entity.Mechanic;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MechanicMapper {
    MechanicMapper INSTANCE = Mappers.getMapper(MechanicMapper.class);

    MechanicDto toMechanicDto(Mechanic mechanic);

    Mechanic toMechanicEntity(MechanicDto mechanicDto);
}
