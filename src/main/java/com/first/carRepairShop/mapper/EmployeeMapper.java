package com.first.carRepairShop.mapper;

import com.first.carRepairShop.dto.EmployeeDto;
import com.first.carRepairShop.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mappings({
            @Mapping(target = "user.username", source = "username"),
            @Mapping(target = "user.password", source = "password"),
            @Mapping(target = "user.name", source = "name"),
            @Mapping(target = "user.lastname", source = "lastname"),
            @Mapping(target = "age", source = "age"),
            @Mapping(target = "user.email", source = "email"),
            @Mapping(target = "user.phone", source = "phone"),
            @Mapping(target = "user.address", source = "address"),
            @Mapping(target = "user.role", source = "role") // maps role.name to String
    })
    EmployeeDto toDto(Employee employee);

    @Mappings({
            @Mapping(source = "user.username", target = "username"),
            @Mapping(source = "user.password", target = "password"),
            @Mapping(source = "user.name", target = "name"),
            @Mapping(source = "user.lastname", target = "lastname"),
            @Mapping(source = "age", target = "age"),
            @Mapping(source = "user.email", target = "email"),
            @Mapping(source = "user.phone", target = "phone"),
            @Mapping(source = "user.address", target = "address"),
            @Mapping(source = "user.role", target = "role") // maps String to role.name
    })
    Employee toEntity(EmployeeDto employeeDto);
}
