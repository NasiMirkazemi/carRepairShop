package com.first.carRepairShop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionDto {
    private Integer permissionId;
    @NotBlank(message = "Permission name is required")
    private String name;
    @NotEmpty(message = "At least one role must be assigned")
    private Set<Integer> roleIds = new HashSet<>();
    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;
}
