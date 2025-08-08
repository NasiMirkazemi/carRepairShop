package com.first.carRepairShop.dto;

import com.first.carRepairShop.entity.Permissions;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class RolesDto {
    private Integer roleId;
    @NotBlank(message = "Role name is required")
    private String name;
}
