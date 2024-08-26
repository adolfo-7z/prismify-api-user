package com.ufro.dci.etransparency.etransparency_api_user.controllers.manager;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ufro.dci.etransparency.etransparency_api_user.handler.ResponseHandler;
import com.ufro.dci.etransparency.etransparency_api_user.services.manager.ManagerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("managers")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getAllManagers(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        return ResponseHandler.generateResponse(OPERATION_SUCCESSFUL, HttpStatus.OK,
                managerService.getAllManagers(page, size));
    }
    
}
