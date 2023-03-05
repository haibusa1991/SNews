package com.snews.server.validators;

import com.snews.server.annotations.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;

public class PasswordValidator implements ConstraintValidator<Password,String> {


    @Override
    public void initialize(Password constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean hasUppercase=false;
        boolean hasLowercase=false;
        boolean hasDigit=false;

        for (char c : value.toCharArray()) {
            if(Character.isUpperCase(c)) {
                hasUppercase=true;
                break;
            }
        }

        for (char c : value.toCharArray()) {
            if(Character.isLowerCase(c)) {
                hasLowercase=true;
                break;
            }
        }

        for (char c : value.toCharArray()) {
            if(Character.isDigit(c)) {
                hasDigit=true;
                break;
            }
        }

        return hasUppercase && hasLowercase && hasDigit;
    }
}
