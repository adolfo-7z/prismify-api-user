package prismify.user.application.usecases;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import prismify.user.domain.models.*;
import prismify.user.domain.ports.in.ManageNotificationUseCase;
import prismify.user.domain.ports.out.UserRepository;

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
        addNotificationToUser(user, message);
    }

    /**
     * Crea una nueva notificación para el usuario administrador.
     *
     * @param message mensaje de la notificación
     */
    @Override
    public void createAdminNotification(String message) {
        User admin = userRepository.findByRole(Role.ADMIN);
        addNotificationToUser(admin, message);
    }

    private void addNotificationToUser(User user, String message) {
        List<Notification> notifications = user.getNotifications() != null
                ? new ArrayList<>(user.getNotifications())
                : new ArrayList<>();
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setDate(LocalDateTime.now());
        notifications.add(notification);
        if (notifications.size() > 10) {
            notifications.remove(0);
        }
        user.setNotifications(notifications);
        userRepository.save(user);
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
