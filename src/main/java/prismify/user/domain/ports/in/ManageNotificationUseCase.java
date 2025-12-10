package prismify.user.domain.ports.in;

import java.util.List;

import prismify.user.domain.models.Notification;

public interface ManageNotificationUseCase {
    void createNotification(Long userId, String message);

    void createAdminNotification(String message);

    List<Notification> getNotifications(Long userId);

    void removeNotification(Long userId, Long id);

    void clearNotifications(Long userId);
}
