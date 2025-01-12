package com.first.carrepairshop.mapper;

import com.first.carrepairshop.dto.MechanicDto;
import com.first.carrepairshop.entity.Mechanic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ServiceMapper.class})
public interface MechanicMapper {
    MechanicMapper INSTANCE = Mappers.getMapper(MechanicMapper.class);

    @Mapping(target = "servicesDto", ignore = true)
    MechanicDto toMechanicDto(Mechanic mechanic);

    @Named("toMechanicDtoWithoutServices")
    @Mapping(target = "servicesDto", ignore = true)
    MechanicDto toMechanicDtoWithoutServices(Mechanic mechanic);

    @Mapping(target = "services", ignore = true)
    Mechanic toMechanicEntity(MechanicDto mechanicDto);

    @Named("toMechanicEntityWithoutServices")
    @Mapping(target = "services", ignore = true)
    Mechanic toMechanicEntityWithoutServices(MechanicDto mechanicDto);

    List<MechanicDto> toMachanicDtoList(List<Mechanic> mechanics);

    List<Mechanic> toMechanicEntityList(List<MechanicDto> mechanicsDto);


}

