package com.first.carRepairShop.mapper;

import com.first.carRepairShop.dto.CarDto;
import com.first.carRepairShop.dto.CarDtoFull;
import com.first.carRepairShop.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = CustomerMapper.class)
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    @Mapping(target = "customerId", source = "customer.id")
    CarDto toCarDto(Car car);

    // 🔹 Convert CarDto → Car Entity (Handles Customer manually)
    @Mapping(target = "customer", ignore = true)
    Car toCarEntity(CarDto carDto);

    @Mapping(target = "customer", source = "customer")
    CarDtoFull toCarDtoWithCustomer(Car car);

    @Mapping(target = "customer", source = "customer")
    Car toCustomerFromCarDtoWithCustomer(CarDtoFull carDtoFull);

    List<CarDto> toCarDtoList(List<Car> carList);

    List<Car> toCarEntityList(List<CarDto> carDtoList);

    @Mapping(target = "carId", ignore = true)

    void updateCarFromCarDto(CarDto carDto, @MappingTarget Car car);


}
