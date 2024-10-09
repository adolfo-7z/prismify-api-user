package com.ufro.dci.etransparency.etransparency_api_user.services.notification;

import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity.UserRole;

public interface NotificationService {

    public Object getUserNotifications(Long userId, UserRole role);

}
