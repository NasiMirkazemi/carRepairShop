package com.first.carRepairShop.mapper;

import com.first.carRepairShop.dto.PermissionDto;
import com.first.carRepairShop.entity.Permissions;
import com.first.carRepairShop.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    @Mapping(target = "roleIds", ignore = true)
    PermissionDto toDto(Permissions permissions);

    @Mapping(target = "role", ignore = true)
    Permissions toEntity(PermissionDto permissionDto);



}

