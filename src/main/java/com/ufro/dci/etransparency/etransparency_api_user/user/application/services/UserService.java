package com.ufro.dci.etransparency.etransparency_api_user.user.application.services;

import java.util.List;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.*;

import lombok.RequiredArgsConstructor;

/**
 * Servicio que implementa los casos de uso relacionados con la gestión de
 * usuarios.
 * <p>
 * Esta clase actúa como un orquestador que delega las operaciones de creación,
 * recuperación, actualización y eliminación de usuarios en sus respectivas
 * dependencias.
 *
 * @author Adolfo Plaza
 */
@RequiredArgsConstructor
public class UserService implements CreateUserUseCase, RetrieveUserUseCase, UpdateUserUseCase, DeleteUserUseCase {

    private final CreateUserUseCase createUserUseCase;
    private final RetrieveUserUseCase retrieveUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    /**
     * Crea un nuevo usuario en el sistema.
     *
     * @param user objeto {@link User} con los datos del usuario a crear
     * @return el usuario creado
     */
    @Override
    public User createUser(User user) {
        return createUserUseCase.createUser(user);
    }

    /**
     * Recupera un usuario por su identificador único.
     *
     * @param id identificador del usuario
     * @return el usuario correspondiente al id proporcionado
     */
    @Override
    public User getUserById(Long id) {
        return retrieveUserUseCase.getUserById(id);
    }

    /**
     * Recupera un usuario por su nombre de usuario.
     *
     * @param username nombre de usuario a buscar
     * @return el usuario correspondiente al username
     */
    @Override
    public User getUserByUsername(String username) {
        return retrieveUserUseCase.getUserByUsername(username);
    }

    /**
     * Obtiene una lista paginada de usuarios con opciones de filtrado y
     * ordenamiento.
     *
     * @param page      número de página
     * @param size      cantidad de usuarios por página
     * @param dateOrder orden de la fecha (ascendente o descendente)
     * @param name      filtro por nombre de usuario
     * @return lista de usuarios
     */
    @Override
    public List<User> getAllUsers(int page, int size, String dateOrder, String name) {
        return retrieveUserUseCase.getAllUsers(page, size, dateOrder, name);
    }

    /**
     * Obtiene las notificaciones de un usuario específico.
     *
     * @param id identificador del usuario
     * @return lista de notificaciones del usuario
     */
    @Override
    public List<String> getUserNotifications(Long id) {
        return retrieveUserUseCase.getUserNotifications(id);
    }

    /**
     * Actualiza los datos de un usuario.
     *
     * @param id          identificador del usuario a actualizar
     * @param updatedUser objeto {@link User} con los nuevos datos
     * @return el usuario actualizado
     */
    @Override
    public User updateUser(Long id, User updatedUser) {
        return updateUserUseCase.updateUser(id, updatedUser);
    }

    /**
     * Activa o desactiva el estado de un usuario.
     *
     * @param id identificador del usuario
     * @return mensaje indicando el nuevo estado del usuario
     */
    @Override
    public String toggleUserStatus(Long id) {
        return updateUserUseCase.toggleUserStatus(id);
    }

    /**
     * Incrementa el número de auditorías realizadas por un usuario.
     *
     * @param id identificador del usuario
     */
    @Override
    public void incrementAuditsPerformed(Long id) {
        updateUserUseCase.incrementAuditsPerformed(id);
    }

    /**
     * Elimina un usuario del sistema.
     *
     * @param id identificador del usuario a eliminar
     * @return mensaje confirmando la eliminación
     */
    @Override
    public String deleteUser(Long id) {
        return deleteUserUseCase.deleteUser(id);
    }

}
