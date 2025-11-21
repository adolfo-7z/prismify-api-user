package com.ufro.dci.etransparency.etransparency_api_user.user.application.usecases;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.Notification;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.Role;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.ManageNotificationUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Implementación del caso de uso para la gestión de notificaciones de usuario.
 * <p>
 * Esta clase permite crear, obtener, eliminar y limpiar notificaciones tanto
 * para usuarios individuales como para el administrador.
 * Utiliza el repositorio de usuarios para acceder y modificar las
 * notificaciones asociadas a cada usuario.
 *
 * <ul>
 * <li>Permite crear notificaciones para un usuario específico o para el
 * administrador.</li>
 * <li>Permite obtener todas las notificaciones de un usuario.</li>
 * <li>Permite eliminar una notificación específica de un usuario.</li>
 * <li>Permite limpiar todas las notificaciones de un usuario.</li>
 * </ul>
 *
 * @author Adolfo Plaza
 */
@RequiredArgsConstructor
public class ManageNotificationUseCaseImpl implements ManageNotificationUseCase {

    private final UserRepository userRepository;

    /**
     * Crea una nueva notificación para un usuario específico.
     *
     * @param userId  identificador único del usuario destinatario
     * @param message mensaje de la notificación
     */
    @Override
    public void createNotification(Long userId, String message) {
        User user = userRepository.findById(userId);
        List<Notification> notifications = new ArrayList<>(user.getNotifications());
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setDate(LocalDateTime.now());
        notifications.add(notification);
        user.setNotifications(notifications);
        userRepository.save(user);
    }

    /**
     * Crea una nueva notificación para el usuario administrador.
     *
     * @param message mensaje de la notificación
     */
    @Override
    public void createAdminNotification(String message) {
        User admin = userRepository.findByRole(Role.ADMIN);
        List<Notification> notifications = admin.getNotifications();
        if (notifications == null) {
            notifications = new ArrayList<>();
        } else {
            notifications = new ArrayList<>(notifications);
        }
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setDate(LocalDateTime.now());
        notifications.add(notification);
        admin.setNotifications(notifications);
        userRepository.save(admin);
    }

    /**
     * Obtiene la lista de notificaciones de un usuario.
     *
     * @param userId identificador único del usuario
     * @return lista de notificaciones asociadas al usuario
     */
    @Override
    public List<Notification> getNotifications(Long userId) {
        User user = userRepository.findById(userId);
        return user.getNotifications();
    }

    /**
     * Elimina una notificación específica de un usuario.
     *
     * @param userId identificador único del usuario
     * @param id     identificador único de la notificación a eliminar
     */
    @Override
    public void removeNotification(Long userId, Long id) {
        User user = userRepository.findById(userId);
        user.getNotifications().removeIf(n -> n.getId().equals(id));
        userRepository.update(user);
    }

    /**
     * Elimina todas las notificaciones de un usuario.
     *
     * @param userId identificador único del usuario
     */
    @Override
    public void clearNotifications(Long userId) {
        User user = userRepository.findById(userId);
        user.getNotifications().clear();
        userRepository.save(user);
    }

}
