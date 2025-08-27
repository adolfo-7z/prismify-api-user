package com.ufro.dci.etransparency.etransparency_api_user.user.application.support.validation.annotation;

import java.lang.annotation.*;
import com.ufro.dci.etransparency.etransparency_api_user.user.application.support.validation.PasswordConstraintValidator;
import jakarta.validation.*;

@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Invalid password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
