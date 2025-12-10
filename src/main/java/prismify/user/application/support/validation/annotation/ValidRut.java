package prismify.user.application.support.validation.annotation;

import java.lang.annotation.*;

import jakarta.validation.*;
import prismify.user.application.support.validation.RutValidator;

@Documented
@Constraint(validatedBy = RutValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRut {
    String message() default "Invalid RUT";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
