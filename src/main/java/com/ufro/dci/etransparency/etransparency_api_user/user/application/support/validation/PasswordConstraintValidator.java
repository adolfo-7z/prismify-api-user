package com.ufro.dci.etransparency.etransparency_api_user.user.application.support.validation;

import com.ufro.dci.etransparency.etransparency_api_user.user.application.support.validation.annotation.ValidPassword;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador de contraseñas que implementa la interfaz
 * {@link ConstraintValidator}
 * para verificar si una cadena cumple con los criterios definidos en la
 * anotación {@link ValidPassword}.
 *
 * <p>
 * Las reglas de validación son:
 * <ul>
 * <li>Debe contener al menos una letra mayúscula.</li>
 * <li>Debe contener al menos una letra minúscula.</li>
 * <li>Debe contener al menos un dígito.</li>
 * <li>Debe contener al menos un carácter especial de la lista:
 * {@code ! @ # $ % ^ & *}.</li>
 * <li>No debe contener espacios en blanco.</li>
 * <li>Debe tener una longitud mínima de 8 caracteres.</li>
 * </ul>
 *
 * <p>
 * Ejemplo de contraseña válida: {@code Segur0!Clave}
 *
 * @author Adolfo Plaza
 */
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    /**
     * Inicializa el validador.
     * En este caso no se requiere lógica de inicialización,
     * por lo que el método se deja vacío.
     *
     * @param constraintAnnotation la anotación {@link ValidPassword} asociada
     */
    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        //Inicializa validador
    }

    /**
     * Valida una contraseña verificando que cumpla con los criterios establecidos.
     *
     * @param password la contraseña a validar
     * @param context  contexto de validación para construir mensajes de error
     *                 personalizados
     * @return {@code true} si la contraseña es válida según las reglas definidas,
     *         {@code false} en caso contrario
     */
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
