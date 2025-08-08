package com.first.carRepairShop.services.impl;

import com.first.carRepairShop.dto.LoginResponse;
import com.first.carRepairShop.dto.PermissionDto;
import com.first.carRepairShop.dto.RolesDto;
import com.first.carRepairShop.entity.Permissions;
import com.first.carRepairShop.entity.Role;
import com.first.carRepairShop.entity.UserApp;
import com.first.carRepairShop.exception.BadRequestException;
import com.first.carRepairShop.exception.NotFoundException;
import com.first.carRepairShop.exception.RoleAlreadyExistException;
import com.first.carRepairShop.mapper.RolesMapper;
import com.first.carRepairShop.repository.PermissionRepository;
import com.first.carRepairShop.repository.RoleRepository;
import com.first.carRepairShop.repository.UserRepository;
import com.first.carRepairShop.services.PermissionService;
import com.first.carRepairShop.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RolesMapper rolesMapper;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final PermissionService permissionService;
    private final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Override
    public RolesDto addRole(RolesDto rolesDto) {
        if (rolesDto == null)
            throw new BadRequestException("Role is required");
        if (roleRepository.existsByName(rolesDto.getName()))
            throw new RoleAlreadyExistException("Role " + rolesDto.getName() + "is already exist");
        Role role = rolesMapper.toEntity(rolesDto);
        roleRepository.save(role);
        return rolesMapper.toDto(role);
    }

    @Override
    public RolesDto updateRole(RolesDto rolesDto) {
        if (rolesDto == null)
            throw new BadRequestException("Role is required");
        Role role = roleRepository.findById(rolesDto.getRoleId())
                .orElseThrow(() -> new NotFoundException("No Role found with id :" + rolesDto.getRoleId()));
        Optional.ofNullable(rolesDto.getName()).ifPresent(name -> role.setName(name));
        Role savedRole = roleRepository.save(role);
        return rolesMapper.toDto(savedRole);
    }

    @Override
    public RolesDto getRoleById(Integer roleId) {
        return null;
    }

    @Override
    public void deleteRole(Integer roleId) {
        if (roleId == null)
            throw new BadRequestException(" Role Id is required");
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("No Role found with id:" + roleId));
        List<UserApp> userApps = userRepository.findAllByRole(role);
        userApps.stream()
                .forEach(userApp -> userApp.setRole(null));
        userRepository.saveAll(userApps);
        roleRepository.delete(role);
        logger.info("Role with id {} has been deleted", roleId);

    }

    @Override
    public void addPermissionToRole(Integer roleId, Integer permissionId) {
        if (roleId == null)
            throw new BadRequestException("Role id is required");
        if (permissionId == null)
            throw new BadRequestException("Permission Id is required");
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("No Role found with id:" + roleId));
        Permissions permissions = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new NotFoundException("No Permission found with id:" + permissionId));
        if (!role.getPermissions().contains(permissions)) {
            role.getPermissions().add(permissions);
        }
        roleRepository.save(role);
        logger.info("Permission with id {} has been added to Role with id {}", permissionId, role);
    }

    @Override
    public void deletePermissionFromRole(Integer roleId, Integer permissionId) {
        if (roleId == null) throw new BadRequestException("Role id is required");
        if (permissionId == null) throw new BadRequestException("Permission id is required");
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("No Role found with id : " + roleId));
        Permissions permissions = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new NotFoundException("No permission found with id: " + permissionId));
        Set<Permissions> permissionsSet = role.getPermissions();
        boolean removedFromRole = permissionsSet.removeIf(permission -> permissions.getPermissionId().equals(permissionId));
        boolean removedFromPermission = permissions.getRole().removeIf(role1 -> role.getRoleId().equals(roleId));
        if (removedFromRole && removedFromPermission) {
            log.info("Permission with id {} has been removed from Role with id {}", permissionId, roleId);
        } else {
            throw new NotFoundException("This permission is not linked to this role.");
        }
        roleRepository.save(role);
        permissionRepository.save(permissions);
    }
}
