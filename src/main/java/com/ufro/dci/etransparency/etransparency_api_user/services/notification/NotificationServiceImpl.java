package com.ufro.dci.etransparency.etransparency_api_user.services.notification;

import java.util.*;
import org.springframework.stereotype.Service;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity.UserRole;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.administrator.AdministratorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.manager.ManagerRepository;
import lombok.RequiredArgsConstructor;
import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final AdministratorRepository administratorRepository;

    private final ManagerRepository managerRepository;

    private final AuditorRepository auditorRepository;

    public Object getUserNotifications(Long userId, UserRole role) {
        UserEntity user = getUserByIdAndRole(userId, role);
        Map<String, Object> response = new HashMap<>();
        
        response.put("notifications", user.getNotifications());
        return response;
    }

    public UserEntity getUserByIdAndRole(Long userId, UserRole role) {
        switch (role) {
            case ADMIN:
                return administratorRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                                THE_MANAGER_WITH_ID + userId + WAS_NOT_FOUND_OR_INACTIVE));
            case AUDITOR:
                return auditorRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                                THE_MANAGER_WITH_ID + userId + WAS_NOT_FOUND_OR_INACTIVE));
            case MANAGER:
                return managerRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND,
                                THE_MANAGER_WITH_ID + userId + WAS_NOT_FOUND_OR_INACTIVE));
            default:
                throw new IllegalArgumentException("Unknown user role: " + role);
        }
    }

}
