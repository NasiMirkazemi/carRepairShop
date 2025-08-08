package com.first.carRepairShop.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SourceValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSource {
    String message() default "Invalid Source Name.Must Be MECHANIC ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
