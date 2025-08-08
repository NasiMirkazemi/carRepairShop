package com.first.carRepairShop.controller;

import com.first.carRepairShop.dto.RolesDto;
import com.first.carRepairShop.services.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/add")
    public ResponseEntity<RolesDto> addRole(@RequestBody @Valid RolesDto rolesDto) {
        RolesDto savedRolesDto = roleService.addRole(rolesDto);
        return ResponseEntity.ok(savedRolesDto);

    }

    @DeleteMapping("/delete/{roleId}")
    public ResponseEntity<String> deleteRole(@PathVariable("roleId") Integer roleId) {

        roleService.deleteRole(roleId);
        return ResponseEntity.ok().body("Role has been deleted with id:" + roleId);

    }

    @PatchMapping("/update")
    public ResponseEntity<RolesDto> update(@RequestBody RolesDto rolesDto) {
        RolesDto updatedRole = roleService.updateRole(rolesDto);
        return ResponseEntity.ok(updatedRole);
    }
}
