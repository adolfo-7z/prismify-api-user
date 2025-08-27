package com.ufro.dci.etransparency.etransparency_api_user.user.application.support.validation.annotation;

import java.lang.annotation.*;

import com.ufro.dci.etransparency.etransparency_api_user.user.application.support.validation.RutValidator;

import jakarta.validation.*;

@Documented
@Constraint(validatedBy = RutValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRut {
    String message() default "Invalid RUT";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
