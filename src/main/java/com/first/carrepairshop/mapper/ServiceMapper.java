package com.first.carrepairshop.mapper;

import com.first.carrepairshop.dto.ServicesDto;
import com.first.carrepairshop.entity.Services;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ServiceMapper {
    ServiceMapper INSTANCE = Mappers.getMapper(ServiceMapper.class);

    ServicesDto toServicesDto(Services service);

    Services toServiceEntity(ServicesDto serviceDto);
}
