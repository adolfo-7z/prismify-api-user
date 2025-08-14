package com.ufro.dci.etransparency.etransparency_api_user.services.notification;

import java.util.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;

import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity.UserRole;
import com.ufro.dci.etransparency.etransparency_api_user.models.administrator.Administrator;
import com.ufro.dci.etransparency.etransparency_api_user.models.auditor.Auditor;
import com.ufro.dci.etransparency.etransparency_api_user.models.manager.Manager;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.administrator.AdministratorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.manager.ManagerRepository;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final AdministratorRepository administratorRepository;

    private final ManagerRepository managerRepository;

    private final AuditorRepository auditorRepository;

    @Override
    @Transactional(readOnly = true)
    public Object getUserNotifications(Long userId, UserRole role) {
        Map<String, Object> response = new HashMap<>();
        response.put("notifications", getNotificationsByIdAndRole(userId, role));
        return response;
    }

    private List<String> getNotificationsByIdAndRole(Long userId, UserRole role) {
        if (role.equals(UserRole.ADMIN)) {
            Administrator admin = administratorRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                            THE_ADMIN_WITH_ID + userId + WAS_NOT_FOUND_OR_INACTIVE));
            return admin.getNotifications();
        } else if (role.equals(UserRole.AUDITOR)) {
            Auditor auditor = auditorRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                            THE_AUDITOR_WITH_ID + userId + WAS_NOT_FOUND_OR_INACTIVE));
            return auditor.getNotifications();
        } else if (role.equals(UserRole.MANAGER)) {
            Manager manager = managerRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                            THE_MANAGER_WITH_ID + userId + WAS_NOT_FOUND_OR_INACTIVE));
            return manager.getNotifications();
        }
        throw new IllegalArgumentException("Unknown user role: " + role);
    }

}
