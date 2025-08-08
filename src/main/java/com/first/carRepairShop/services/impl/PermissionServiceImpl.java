package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.dto.PermissionDto;
import com.first.carRepairShop.entity.Permissions;
import com.first.carRepairShop.entity.Role;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.exception.PermissionExistException;
import com.first.carRepairShop.exception.RoleAlreadyExistException;
import com.first.carRepairShop.mapper.PermissionMapper;
import com.first.carRepairShop.repository.PermissionRepository;
import com.first.carRepairShop.repository.RoleRepository;
import com.first.carRepairShop.services.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    private final RoleRepository roleRepository;

    public Permissions toEntity(PermissionDto permissionDto) {
        Permissions permissions = permissionMapper.toEntity(permissionDto);
        if (permissionDto.getRoleIds() != null && !permissionDto.getRoleIds().isEmpty()) {
            Set<Role> roleSet = permissionDto.getRoleIds().stream()
                    .map(id -> roleRepository.findById(id)
                            .orElseThrow(() -> new NotFoundException("No Role found with id:" + id)))
                    .peek(role -> role.getPermissions().add(permissions))
                    .collect(Collectors.toSet());
            permissions.setRole(roleSet);
        }
        return permissions;
    }


    public PermissionDto toDto(Permissions permissions) {
        PermissionDto permissionDto = permissionMapper.toDto(permissions);
        if (permissions.getRole() != null && !permissions.getRole().isEmpty()) {
            Set<Integer> roleIds = permissions.getRole().stream()
                    .map(role -> role.getRoleId())
                    .collect(Collectors.toSet());
            permissionDto.setRoleIds(roleIds);
        }
        return permissionDto;
    }

    @Override
    public PermissionDto addPermission(PermissionDto permissionDto) {
        if (permissionDto == null)
            throw new BadRequestException("permission is required");
        if (permissionRepository.existsByName(permissionDto.getName()))
            throw new PermissionExistException("Permission with name:" + permissionDto.getName() + " is already exist");

        Permissions permissions = toEntity(permissionDto);
        Permissions savedPermission = permissionRepository.save(permissions);
        return toDto(savedPermission);
    }

    @Override
    @Transactional
    public PermissionDto updatePermission(PermissionDto permissionDto) {
        if (permissionDto == null)
            throw new BadRequestException("Permission is required");
        Permissions permissions = permissionRepository.findById(permissionDto.getPermissionId())
                .orElseThrow(() -> new NotFoundException("No Permission found with id:" + permissionDto.getPermissionId()));
        Optional.ofNullable(permissionDto.getName()).ifPresent(name -> permissions.setName(name));
        Optional.ofNullable(permissionDto.getDescription()).ifPresent(description -> permissions.setDescription(description));
        if (permissionDto.getRoleIds() != null && !permissionDto.getRoleIds().isEmpty()) {
            Set<Integer> existingIds = permissions.getRole().stream()
                    .map(Role::getRoleId)
                    .collect(Collectors.toSet());
            permissionDto.getRoleIds().stream()
                    .filter(id -> !existingIds.contains(id))
                    .forEach(id -> {
                        Role role = roleRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("No Role found with id :" + id));
                        permissions.getRole().add(role);
                    });
        }
        Permissions savedPermission = permissionRepository.save(permissions);
        return permissionMapper.toDto(savedPermission);
    }

    @Override
    @Transactional
    public void deletePermission(Integer permissionId) {
        if (permissionId == null) throw new BadRequestException("Permission id is required");
        Permissions permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new NotFoundException("No Permission found with id: " + permissionId));
        permission.getRole().stream()
                .forEach(role -> role.getPermissions().remove(permission));
        permissionRepository.delete(permission);
        log.info("Permission with id {} has been deleted", permission.getPermissionId());

    }

    @Override
    public PermissionDto getPermission(Integer permissionId) {
        if (permissionId == null) throw new BadRequestException("Permission id is required");
        Permissions permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new NotFoundException("No Permission found with id: " + permissionId));
        return permissionMapper.toDto(permission);


    }
}
