package com.first.carRepairShop.services;

import com.first.carRepairShop.dto.PermissionDto;
import com.first.carRepairShop.dto.RolesDto;
import com.first.carRepairShop.entity.Permissions;

public interface RoleService {

    RolesDto addRole(RolesDto rolesDto);
    RolesDto updateRole(RolesDto rolesDto);
    RolesDto getRoleById(Integer roleId);
    void deleteRole(Integer roleId);
    void addPermissionToRole(Integer roleId,Integer permissionId);
    void deletePermissionFromRole(Integer roleId,Integer permissionId);
}
