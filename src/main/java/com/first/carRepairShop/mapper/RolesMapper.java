package com.first.carRepairShop.mapper;

import com.first.carRepairShop.dto.RolesDto;
import com.first.carRepairShop.entity.Permissions;
import com.first.carRepairShop.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RolesMapper {
    // ✅ Map Role → RolesDto (ignore permissions)
    @Mapping(target = "name", source = "name")
    RolesDto toDto(Role role);

    // ✅ Map RolesDto → Role (ignore permissions completely)
    @Mapping(target = "permissions", ignore = true)
    Role toEntity(RolesDto rolesDto);

    // ✅ Update Role entity from RolesDto (only update name, ignore permissions)
    @Mapping(target = "permissions", ignore = true)
    void updateRoleFromDto(RolesDto dto, @MappingTarget Role entity);

}
