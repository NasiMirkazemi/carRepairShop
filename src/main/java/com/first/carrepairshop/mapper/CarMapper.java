package com.first.carrepairshop.mapper;

import com.first.carrepairshop.dto.CarDto;
import com.first.carrepairshop.dto.CustomerDto;
import com.first.carrepairshop.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    @Mapping(target = "customerDto", source = "car.customer")
    CarDto toCarDto(Car car);

    @Mapping(target = "customer", source = "carDto.customerDto")
    Car toCarEntity(CarDto carDto);


}
