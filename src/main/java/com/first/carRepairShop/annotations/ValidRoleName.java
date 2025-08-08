package com.first.carRepairShop.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented //Tells JavaDoc tools to include this annotation in the documentation of the class where it is used.
@Constraint(validatedBy = RoleNameValidator.class)//“RoleNameValidator.class will handle the logic for this annotation.”
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRoleName {
    String message() default "Invalid role name. Must be MECHANIC"; //an abstract method with a default value.

    Class<?>[] groups() default {};

    /*Allow this annotation to be used with groups like CreateGroup or UpdateGroup if someone wants to
     Even if you don’t use groups right now, you must include this method in a custom annotation because Bean Validation requires it.*/
    Class<? extends Payload>[] payload() default {};

}
