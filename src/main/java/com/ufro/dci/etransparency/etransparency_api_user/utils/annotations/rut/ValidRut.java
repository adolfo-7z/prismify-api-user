package com.ufro.dci.etransparency.etransparency_api_user.utils.annotations.rut;

import java.lang.annotation.*;
import jakarta.validation.*;

@Constraint(validatedBy = RutValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRut {

    String message() default "Invalid RUT";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
