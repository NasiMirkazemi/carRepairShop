package com.first.carrepairshop.mapper;

import com.first.carrepairshop.dto.EmployeeDto;
import com.first.carrepairshop.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDto toEmployeeDto(Employee employee);

    Employee toEmployeeEntity(EmployeeDto employeeDto);
}
