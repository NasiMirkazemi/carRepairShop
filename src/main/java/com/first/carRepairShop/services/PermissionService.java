package com.first.carRepairShop.services;

import com.first.carRepairShop.dto.PermissionDto;

public interface PermissionService {
    PermissionDto addPermission(PermissionDto permissionDto);
    PermissionDto updatePermission(PermissionDto permissionDto);
    void deletePermission(Integer permissionId);
    PermissionDto getPermission(Integer permissionId);
}
