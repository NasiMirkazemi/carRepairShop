package com.first.carRepairShop.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class SourceValidator implements ConstraintValidator<ValidSource, String> {

    private static final Set<String> VALID_SOURCE = Set.of("Mechanic", "System");

    @Override
    public void initialize(ValidSource constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String source, ConstraintValidatorContext context) {
        if (source == null)
            return false;
        return VALID_SOURCE.contains(source.toUpperCase());
    }
}
