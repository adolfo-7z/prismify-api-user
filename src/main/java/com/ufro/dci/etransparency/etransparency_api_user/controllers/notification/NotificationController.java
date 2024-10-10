package com.ufro.dci.etransparency.etransparency_api_user.controllers.notification;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ufro.dci.etransparency.etransparency_api_user.handler.ResponseHandler;
import com.ufro.dci.etransparency.etransparency_api_user.services.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;

@RestController
@RequestMapping("notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('AUDITOR') or hasRole('MANAGER')")
    public ResponseEntity<Object> getAllManagers(@RequestParam(name = "user") Long userId,
            @RequestParam(name = "role") String userRole) {
        return ResponseHandler.generateResponse(OPERATION_SUCCESSFUL, HttpStatus.OK,
                notificationService.getUserNotifications(userId, userRole));
    }

}
