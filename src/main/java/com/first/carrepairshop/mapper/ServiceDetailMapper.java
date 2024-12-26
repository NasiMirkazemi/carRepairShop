package com.first.carrepairshop.mapper;

import com.first.carrepairshop.associations.ServiceDetail;
import com.first.carrepairshop.dto.ServiceDetailDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ServiceDetailMapper {
    ServiceDetailMapper INSTANCE = Mappers.getMapper(ServiceDetailMapper.class);

    ServiceDetailDto toServiceDetailDto(ServiceDetail serviceDetail);

    ServiceDetail toServiceDetailEntity(ServiceDetailDto serviceDetailDto);

}
