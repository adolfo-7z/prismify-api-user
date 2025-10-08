package com.ufro.dci.etransparency.etransparency_api_user.user.application.usecases;

import java.time.LocalDateTime;
import java.util.List;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.Notification;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.ManageNotificationUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ManageNotificationUseCaseImpl implements ManageNotificationUseCase {

    private final UserRepository userRepository;

    @Override
    public void createNotification(Long userId, String message) {
        User user = userRepository.findById(userId);
        List<Notification> notifications = user.getNotifications();
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setDate(LocalDateTime.now());
        notifications.add(notification);
        user.setNotifications(notifications);
        userRepository.save(user);
    }

    @Override
    public List<Notification> getNotifications(Long userId) {
        User user = userRepository.findById(userId);
        return user.getNotifications();
    }

    @Override
    public void removeNotification(Long userId, Long id) {
        User user = userRepository.findById(id);
        List<Notification> notifications = user.getNotifications();
        notifications.removeIf(n -> n.getId().equals(id));
        userRepository.save(user);
    }

    @Override
    public void clearNotifications(Long userId) {
        User user = userRepository.findById(userId);
        user.getNotifications().clear();
        userRepository.save(user);
    }

}
