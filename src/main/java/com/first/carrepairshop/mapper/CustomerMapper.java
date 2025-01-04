package com.first.carrepairshop.mapper;

import com.first.carrepairshop.dto.CustomerDto;
import com.first.carrepairshop.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "invoicesDto", source = "invoices")
    @Mapping(target = "carsDto", source = "cars")
    CustomerDto toCustomerDto(Customer customer);

    @Mapping(target = "invoices", source = "invoicesDto")
    @Mapping(target = "cars", source = "carsDto")
    Customer toCustomerEntity(CustomerDto customerDto);


}
