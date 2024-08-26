package com.ufro.dci.etransparency.etransparency_api_user.controllers.manager;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.manager.ManagerDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.manager.ManagerUpdateDTO;
import com.ufro.dci.etransparency.etransparency_api_user.handler.ResponseHandler;
import com.ufro.dci.etransparency.etransparency_api_user.services.manager.ManagerCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("managers")
@RequiredArgsConstructor
public class ManagerCrudController {

    private final ManagerCrudService managerCrudService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<Object> getManager(@PathVariable Long id) {
        return ResponseHandler.generateResponse(OPERATION_SUCCESSFUL, HttpStatus.OK,
                managerCrudService.getManager(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createManager(@Valid @RequestBody ManagerDTO managerDTO) {
        return ResponseHandler.generateResponse(OPERATION_SUCCESSFUL, HttpStatus.CREATED,
                managerCrudService.createManager(managerDTO));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<Object> updateManager(@PathVariable Long id,
            @Valid @RequestBody ManagerUpdateDTO managerUpdateDTO) {
        return ResponseHandler.generateResponse(OPERATION_SUCCESSFUL, HttpStatus.OK,
                managerCrudService.updateManager(id, managerUpdateDTO));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> toggleManagerStatus(@PathVariable Long id) {
        return ResponseHandler.generateResponse(OPERATION_SUCCESSFUL, HttpStatus.OK,
                managerCrudService.toggleManagerStatus(id));
    }

}
