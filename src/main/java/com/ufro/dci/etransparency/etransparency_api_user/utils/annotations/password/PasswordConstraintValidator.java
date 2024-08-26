package com.ufro.dci.etransparency.etransparency_api_user.utils.annotations.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        boolean hasUppercase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLowercase = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecialChar = password.chars().anyMatch(ch -> "!@#$%^&*".indexOf(ch) >= 0);
        boolean noWhitespace = password.chars().noneMatch(Character::isWhitespace);

        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar && noWhitespace && password.length() >= 8;
    }
}
