package com.first.carRepairShop.controller;

import com.first.carRepairShop.dto.PermissionDto;
import com.first.carRepairShop.services.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    @PostMapping("/add")
    public ResponseEntity<PermissionDto> AddPermission(@RequestBody @Valid PermissionDto permissionDto) {
        PermissionDto savedPermission = permissionService.addPermission(permissionDto);
        return ResponseEntity.ok(savedPermission);
    }


}
