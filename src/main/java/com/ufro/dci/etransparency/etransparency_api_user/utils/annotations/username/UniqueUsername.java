package com.ufro.dci.etransparency.etransparency_api_user.utils.annotations.username;

import java.lang.annotation.*;
import jakarta.validation.*;

@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    String message() default "Username already in use";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
