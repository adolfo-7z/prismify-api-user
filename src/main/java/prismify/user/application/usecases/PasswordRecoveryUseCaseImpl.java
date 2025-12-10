package prismify.user.application.usecases;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import prismify.user.domain.models.User;
import prismify.user.domain.ports.in.PasswordRecoveryUseCase;
import prismify.user.domain.ports.out.*;
import prismify.user.infrastructure.controllers.exception.custom.*;

/**
 * Implementación del caso de uso para la recuperación de contraseñas.
 * 
 * <p>
 * Esta clase maneja el flujo de envío y validación de códigos de recuperación,
 * así como la validación y actualización de contraseñas nuevas para los
 * usuarios.
 * Se apoya en {@link UserRepository}, {@link UserEmailPort} y
 * {@link PasswordHasher}
 * para acceder a los datos, enviar notificaciones y procesar contraseñas de
 * manera segura.
 *
 * @author Adolfo Plaza
 */
@RequiredArgsConstructor
public class PasswordRecoveryUseCaseImpl implements PasswordRecoveryUseCase {

    private final UserRepository userRepository;
    private final UserEmailPort mailPort;
    private final PasswordHasher hasher;

    /**
     * Envía un código de recuperación al correo electrónico del usuario.
     * <p>
     * El código es válido por 30 minutos. Se guarda en el repositorio
     * asociado al usuario y se envía mediante el puerto de correo.
     *
     * @param email dirección de correo del usuario al que se enviará el código
     * @throws RuntimeException si el usuario no existe o no se puede guardar la
     *                          información
     */
    @Override
    public void sendRecoveryCode(String email) {
        User user = userRepository.findByEmail(email);
        String recoveryCode = UUID.randomUUID().toString().substring(0, 6);
        user.setRecoveryCode(recoveryCode);
        user.setRecoveryCodeExpiration(LocalDateTime.now().plusMinutes(30));
        userRepository.save(user);
        mailPort.sendRecoveryCodeEmail(user.getEmail(), recoveryCode);
    }

    /**
     * Valida que el código de recuperación proporcionado sea correcto y no esté
     * expirado.
     *
     * @param email        dirección de correo del usuario
     * @param recoveryCode código de recuperación ingresado por el usuario
     * @throws InvalidRecoveryCodeException si el código es nulo, vacío, incorrecto
     *                                      o expirado
     */
    @Override
    public void validateRecoveryCode(String email, String recoveryCode) {
        User user = userRepository.findByEmail(email);
        String userRecoveryCode = user.getRecoveryCode();
        if (userRecoveryCode == null || userRecoveryCode.isEmpty()) {
            throw new InvalidRecoveryCodeException();
        }
        LocalDateTime expiration = user.getRecoveryCodeExpiration();
        if (expiration == null || expiration.isBefore(LocalDateTime.now())) {
            throw new InvalidRecoveryCodeException("The recovery code has expired.");
        }
        if (!userRecoveryCode.equals(recoveryCode)) {
            throw new InvalidRecoveryCodeException();
        }
    }

    /**
     * Valida y actualiza la contraseña de un usuario.
     * 
     * <p>
     * La nueva contraseña debe cumplir con los siguientes requisitos:
     * </p>
     * <ul>
     * <li>Coincidir con la confirmación recibida.</li>
     * <li>No ser igual a la contraseña actual.</li>
     * </ul>
     *
     * <p>
     * Si la contraseña es válida, se actualiza y se envía
     * una alerta al correo electrónico del usuario.
     *
     * @param email              dirección de correo del usuario
     * @param password           nueva contraseña ingresada
     * @param validationPassword confirmación de la nueva contraseña
     * @throws InvalidPasswordException si las contraseñas no coinciden o si es
     *                                  igual a la anterior
     */
    @Override
    public void validateNewPassword(String email, String password, String validationPassword) {
        User user = userRepository.findByEmail(email);
        if (!password.equals(validationPassword)) {
            throw new InvalidPasswordException("Received passwords do not match");
        }
        if (hasher.matches(password, user.getPassword())) {
            throw new InvalidPasswordException(
                    "New password can not be the same as previous password");
        }
        user.setPassword(hasher.hash(password));
        userRepository.save(user);
        mailPort.sendNewPasswordAlert(user.getEmail());
    }

}
