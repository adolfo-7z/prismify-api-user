package prismify.user.application.support.validation.annotation;

import java.lang.annotation.*;

import jakarta.validation.*;
import prismify.user.application.support.validation.UniqueUsernameValidator;

@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    String message() default "Username already in use";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
