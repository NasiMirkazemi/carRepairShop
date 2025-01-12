package com.first.carrepairshop.mapper;

import com.first.carrepairshop.dto.ServicesDto;
import com.first.carrepairshop.entity.Services;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MechanicMapper.class})
public interface ServiceMapper {
    ServiceMapper INSTANCE = Mappers.getMapper(ServiceMapper.class);

    @Mapping(target = "mechanicsDto", source = "mechanics", qualifiedByName = "toMechanicDtoWithoutServices")
    ServicesDto toServicesDto(Services service);

    @Mapping(target = "mechanics", source = "mechanicsDto", qualifiedByName = "toMechanicEntityWithoutServices")
    Services toServiceEntity(ServicesDto serviceDto);

    List<ServicesDto> toServiceDtoList(List<Services> services);

    List<Services> toServiceEntityList(List<ServicesDto> servicesDto);


}
