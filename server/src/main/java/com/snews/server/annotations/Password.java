package com.snews.server.annotations;

import com.snews.server.validators.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {

    String message() default "password should contain an uppercase letter, a lowercase letter, and a digit";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
