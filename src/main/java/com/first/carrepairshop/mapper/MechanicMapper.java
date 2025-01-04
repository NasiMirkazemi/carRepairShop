package com.first.carrepairshop.mapper;

import com.first.carrepairshop.dto.MechanicDto;
import com.first.carrepairshop.entity.Mechanic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ServiceMapper.class})
public interface MechanicMapper {
    MechanicMapper INSTANCE = Mappers.getMapper(MechanicMapper.class);

    @Mapping(target = "servicesDto", source = "services")
    MechanicDto toMechanicDto(Mechanic mechanic);

    @Mapping(target = "services", source = "servicesDto")
    Mechanic toMechanicEntity(MechanicDto mechanicDto);

    List<Mechanic> toEntityList(List<MechanicDto> mechanicsDto);

    List<MechanicDto> toDtoList(List<Mechanic> mechanics);
}

