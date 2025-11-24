package com.ufro.dci.etransparency.etransparency_api_user.user.application.usecases;

import org.springframework.data.domain.Page;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.Role;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.RetrieveUserUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.UserRepository;

import lombok.RequiredArgsConstructor;

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
    public Page<User> getAllUsers(int page, int size, String username, String email, String date, Boolean active, Role role) {
        return userRepository.findAll(page, size, username, email, date, active, role);
    }

}
