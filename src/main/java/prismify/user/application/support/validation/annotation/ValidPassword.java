package prismify.user.application.support.validation.annotation;

import java.lang.annotation.*;

import jakarta.validation.*;
import prismify.user.application.support.validation.PasswordConstraintValidator;

@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Invalid password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
