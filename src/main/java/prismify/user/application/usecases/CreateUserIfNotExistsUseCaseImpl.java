package prismify.user.application.usecases;

import java.util.ArrayList;

import lombok.RequiredArgsConstructor;
import prismify.user.domain.models.Role;
import prismify.user.domain.models.User;
import prismify.user.domain.ports.in.CreateUserIfNotExistsUseCase;
import prismify.user.domain.ports.out.UserRepository;

/**
 * Implementación del caso de uso {@link CreateUserIfNotExistsUseCase} que se
 * encarga de
 * verificar si existe un usuario con rol de administrador en el sistema y, en
 * caso contrario,
 * crea uno con los datos proporcionados.
 *
 * <p>
 * Esta clase utiliza un {@link UserRepository} para consultar la existencia de
 * usuarios
 * y persistir la creación de un administrador por defecto.
 *
 * <p>
 * Se asegura que, si ya existe un administrador, no se creen duplicados.
 *
 * @author Adolfo Plaza
 */
@RequiredArgsConstructor
public class CreateUserIfNotExistsUseCaseImpl implements CreateUserIfNotExistsUseCase {

    private final UserRepository userRepository;

    /**
     * Crea un usuario administrador si no existe ninguno en el sistema.
     *
     * <p>
     * El método verifica si ya existe un usuario con el rol {@link Role#ADMIN}.
     * En caso de que no exista, crea un nuevo usuario administrador con los datos
     * proporcionados, asigna valores iniciales a sus métricas y lo guarda en el
     * repositorio.
     *
     * @param username nombre de usuario del administrador a crear.
     * @param email    correo electrónico del administrador a crear.
     * @param password contraseña del administrador a crear.
     */
    @Override
    public void createAdminIfMissing(String username, String email, String password) {
        boolean exists = userRepository.existsByRole(Role.ADMIN);
        if (exists) {
            return;
        }
        User admin = new User();
        admin.setUsername(username);
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setRole(Role.ADMIN);
        admin.setActive(true);
        admin.setTotalInstitutions(0L);
        admin.setAuditsPerformed(0L);
        admin.setNotifications(new ArrayList<>());
        userRepository.save(admin);
    }

}
