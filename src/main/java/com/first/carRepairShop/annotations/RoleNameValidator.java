package com.first.carRepairShop.annotations;

import com.first.carRepairShop.dto.RolesDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class RoleNameValidator implements ConstraintValidator<ValidRoleName, RolesDto> {
    private static final Set<String> VALID_ROLES = Set.of("MECHANIC", "CUSTOMER", "EMPLOYEE");

    @Override
    public boolean isValid(RolesDto rolesDto, ConstraintValidatorContext context) {
        if (rolesDto == null || rolesDto.getName() == null) return false;
        return VALID_ROLES.contains(rolesDto.getName().toUpperCase());
    }
}
