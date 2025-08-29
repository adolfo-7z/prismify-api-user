package com.ufro.dci.etransparency.etransparency_api_user.user.application.support.validation;

import com.ufro.dci.etransparency.etransparency_api_user.user.application.support.validation.annotation.UniqueEmail;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador que verifica que un correo electrónico sea único en el sistema.
 * <p>
 * Implementa {@link ConstraintValidator} para la anotación personalizada
 * {@link UniqueEmail}
 * y valida cadenas de texto que representan correos electrónicos.
 * <p>
 * Utiliza el {@link UserRepository} para comprobar si el correo electrónico ya
 * existe
 * en la base de datos.
 * 
 * @author Adolfo Plaza
 */
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;

    public UniqueEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Inicializa el validador.
     * <p>
     * Este método se deja intencionalmente vacío porque no se requiere
     * lógica de inicialización para este validador.
     * </p>
     *
     * @param constraintAnnotation la anotación de restricción asociada
     */
    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        // Inicializa validador
    }

    /**
     * Valida que el correo electrónico proporcionado sea único.
     *
     * @param email   el correo electrónico a validar
     * @param context contexto de validación que puede ser usado para construir
     *                mensajes de error
     * @return {@code true} si el correo electrónico no existe en la base de datos,
     *         {@code false} en caso contrario
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !userRepository.existsByEmail(email);
    }

}
