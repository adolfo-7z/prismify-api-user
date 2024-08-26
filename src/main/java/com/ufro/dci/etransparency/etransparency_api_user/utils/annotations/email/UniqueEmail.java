package com.ufro.dci.etransparency.etransparency_api_user.utils.annotations.email;

import java.lang.annotation.*;
import jakarta.validation.*;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "Email already in use";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
