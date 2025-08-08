package com.first.carRepairShop.mapper;

import com.first.carRepairShop.dto.MechanicDto;
import com.first.carRepairShop.dto.MechanicRegister;
import com.first.carRepairShop.entity.Mechanic;
import com.first.carRepairShop.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RolesMapper.class})
public interface MechanicMapper {
    MechanicMapper INSTANCE = Mappers.getMapper(MechanicMapper.class);


    @Mapping(target = "workLogs", source = "workLogs")
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
    MechanicDto toDto(Mechanic mechanic);

    @Mapping(target = "workLogs", source = "workLogs")
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
    Mechanic toEntity(MechanicDto mechanicDto);

    @Mappings({
            @Mapping(source = "id", target = "id"), // ✅ Add this line to map ID!
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
    MechanicRegister toMechanicRegister(Mechanic mechanic);

    @Mapping(target = "workLogs", ignore = true)
    @Mappings({
            @Mapping(source = "user.username", target = "username"),
            @Mapping(source = "user.password", target = "password"),
            @Mapping(source = "user.name", target = "name"),
            @Mapping(source = "user.lastname", target = "lastname"),
            @Mapping(source = "user.email", target = "email"),
            @Mapping(source = "user.phone", target = "phone"),
            @Mapping(source = "user.address", target = "address"),
            @Mapping(source = "user.role", target = "role"),
            @Mapping(source = "age", target = "age"),
            @Mapping(source = "salary", target = "salary"),
            @Mapping(source = "department", target = "department"),
            @Mapping(source = "specialty", target = "specialty"),
            @Mapping(source = "certificate", target = "certificate"),
            @Mapping(source = "hourlyRate", target = "hourlyRate"),
            @Mapping(target = "hireDate", ignore = true),
            @Mapping(target = "id", ignore = true) // Let JPA handle the ID unless you're editing
    })
    Mechanic toEntityFromRegister(MechanicRegister mechanicRegister);

    List<MechanicDto> toMechanicDtoList(List<Mechanic> mechanics);

    List<Mechanic> toMechanicList(List<MechanicDto> mechanicDtos);


}

