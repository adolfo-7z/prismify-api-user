package com.ufro.dci.etransparency.etransparency_api_user.services.auditor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor.AuditorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor.AuditorUpdateDTO;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity.UserRole;
import com.ufro.dci.etransparency.etransparency_api_user.models.auditor.Auditor;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.services.auditor.AuditorCrudServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditorCrudServiceImplTest {

    @Mock
    private AuditorRepository auditorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuditorCrudServiceImpl auditorCrudService;

    private Auditor auditor;
    private AuditorDTO auditorDTO;
    private AuditorUpdateDTO auditorUpdateDTO;

    @BeforeEach
    void setUp() {
        auditor = new Auditor();
        auditor.setActive(true);

        auditorDTO = new AuditorDTO();
        auditorDTO.setPassword("password");

        auditorUpdateDTO = new AuditorUpdateDTO();
    }

    @Test
    void getAuditor_ShouldReturnAuditorDTO() {
        when(auditorRepository.findById(any(Long.class))).thenReturn(Optional.of(auditor));

        AuditorDTO result = auditorCrudService.getAuditor(1L);

        assertNotNull(result);
    }

    @Test
    void getAuditor_ShouldThrowResourceNotFoundException() {
        when(auditorRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> auditorCrudService.getAuditor(1L));
    }

    @Test
    void createAuditor_ShouldReturnAuditorDTO() {
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(auditorRepository.save(any(Auditor.class))).thenAnswer(invocation -> {
            Auditor audit = invocation.getArgument(0);
            audit.setId(1L);
            return audit;
        });

        AuditorDTO result = auditorCrudService.createAuditor(auditorDTO);

        assertNotNull(result);
        assertEquals(UserRole.AUDITOR, result.getRole());
    }

    @Test
    void updateAuditor_ShouldReturnUpdatedAuditorDTO() {
        when(auditorRepository.findById(any(Long.class))).thenReturn(Optional.of(auditor));
        when(auditorRepository.save(any(Auditor.class))).thenReturn(auditor);

        AuditorDTO result = auditorCrudService.updateAuditor(1L, auditorUpdateDTO);

        assertNotNull(result);
    }

    @Test
    void updateAuditor_ShouldThrowResourceNotFoundException() {
        when(auditorRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> auditorCrudService.updateAuditor(1L, auditorUpdateDTO));
    }

    @Test
    void toggleAuditorStatus_ShouldReturnUpdatedAuditorDTO() {
        when(auditorRepository.findById(any(Long.class))).thenReturn(Optional.of(auditor));
        when(auditorRepository.save(any(Auditor.class))).thenReturn(auditor);

        AuditorDTO result = auditorCrudService.toggleAuditorStatus(1L);

        assertNotNull(result);
        verify(auditorRepository, times(1)).save(any(Auditor.class));
    }

    @Test
    void toggleAuditorStatus_ShouldThrowResourceNotFoundException() {
        when(auditorRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> auditorCrudService.toggleAuditorStatus(1L));
    }
}