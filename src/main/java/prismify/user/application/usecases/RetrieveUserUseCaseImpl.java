package prismify.user.application.usecases;

import org.springframework.data.domain.Page;

import lombok.RequiredArgsConstructor;
import prismify.user.domain.models.Role;
import prismify.user.domain.models.User;
import prismify.user.domain.ports.in.RetrieveUserUseCase;
import prismify.user.domain.ports.out.UserRepository;

/**
 * Implementación de la interfaz {@link RetrieveUserUseCase} que se encarga de
 * recuperar información de los usuarios a través del repositorio
 * {@link UserRepository}.
 * <p>
 * Esta clase utiliza inyección de dependencias mediante el constructor para
 * obtener una instancia de {@link UserRepository}.
 *
 * @author Adolfo Plaza
 */
@RequiredArgsConstructor
public class RetrieveUserUseCaseImpl implements RetrieveUserUseCase {

    private final UserRepository userRepository;

    /**
     * Recupera un usuario a partir de su identificador único.
     *
     * @param id identificador único del usuario
     * @return el usuario correspondiente al identificador
     */
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Recupera un usuario a partir de su nombre de usuario.
     *
     * @param username nombre de usuario
     * @return el usuario correspondiente al nombre de usuario
     */
    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Recupera un usuario por su correo electrónico.
     *
     * @param email correo electrónico a buscar
     * @return el usuario correspondiente al email
     */
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Obtiene una lista paginada de usuarios con posibilidad de ordenar por fecha
     * y filtrar por nombre.
     *
     * @param page      número de página solicitada (comenzando en 0)
     * @param size      cantidad de elementos por página
     * @param dateOrder ordenamiento por fecha ("asc" o "desc")
     * @param name      filtro opcional por nombre
     * @return lista de usuarios que cumplen con los criterios de búsqueda
     */
    @Override
    public Page<User> getAllUsers(int page, int size, String username, String email, String date, Boolean active,
            Role role) {
        return userRepository.findAll(page, size, username, email, date, active, role);
    }

}
