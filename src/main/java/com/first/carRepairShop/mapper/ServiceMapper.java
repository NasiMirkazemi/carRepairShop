package com.first.carRepairShop.mapper;

import com.first.carRepairShop.dto.ServiceDto;
import com.first.carRepairShop.entity.Services;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceMapper INSTANCE = Mappers.getMapper(ServiceMapper.class);

    ServiceDto toDto(Services services);// Ignoring mechanics since it's missing in DTO

    Services toEntity(ServiceDto serviceDto);// Empty mechanics list

    List<ServiceDto> toDtoList(List<Services> services);

    List<Services> toEntityList(List<ServiceDto> serviceDtos);


}



