package com.first.carrepairshop.mapper;

import com.first.carrepairshop.dto.ServicesDto;
import com.first.carrepairshop.entity.Services;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring",uses = {MechanicMapper.class})
public interface ServiceMapper {
    ServiceMapper INSTANCE = Mappers.getMapper(ServiceMapper.class);

    @Mapping(target = "mechanicsDto", source = "mechanics")
    ServicesDto toServicesDto(Services service);

    @Mapping(target = "mechanics", source = "mechanicsDto")
    Services toServiceEntity(ServicesDto serviceDto);

    List<ServicesDto> toDtoList(List<Services> services);

    List<Services> toEntityList(List<ServicesDto> servicesDto);
}
