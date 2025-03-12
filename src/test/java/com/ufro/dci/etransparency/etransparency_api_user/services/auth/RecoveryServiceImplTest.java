package com.ufro.dci.etransparency.etransparency_api_user.services.auth;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ufro.dci.etransparency.etransparency_api_user.TransparencyApiUserApplication;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.EmailSendException;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity;
import com.ufro.dci.etransparency.etransparency_api_user.models.administrator.Administrator;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.administrator.AdministratorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.manager.ManagerRepository;
import com.ufro.dci.etransparency.etransparency_api_user.services.email.EmailService;

@SpringBootTest(classes = TransparencyApiUserApplication.class)
@AutoConfigureMockMvc
public class RecoveryServiceImplTest {

    @Autowired
    private RecoveryService recoveryService;

    @MockBean
    private AdministratorRepository administratorRepository;

    @MockBean
    private ManagerRepository managerRepository;

    @MockBean
    private AuditorRepository auditorRepository;

    @MockBean
    private EmailService emailService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void testSendRecoveryCode_Success() throws Exception {

        String email = "prueba@ejemplo.cl";
        UserEntity user = new Administrator();
        user.setEmail(email);
        user.setUsername("Juan Fulano");

        when(administratorRepository.findByEmail(email)).thenReturn(Optional.of((Administrator) user));

        doNothing().when(emailService).sendHtmlEmail(eq(email), eq("Código de recuperación"), anyString());

        String response = recoveryService.sendRecoveryCode(email);

        verify(administratorRepository, times(1)).findByEmail(email);
        verify(emailService, times(1)).sendHtmlEmail(eq(email), eq("Código de recuperación"), anyString());
        assertEquals("Recovery code sent", response);
        assertNotNull(user.getRecoveryCode());
        assertNotNull(user.getRecoveryCodeExpiration());
    }

    @Test
    void testSendRecoveryCode_EmailSendFailure() throws Exception {

        String email = "prueba@ejemplo.cl";
        UserEntity user = new Administrator();
        user.setEmail(email);
        user.setUsername("Juan Fulano");

        when(administratorRepository.findByEmail(email)).thenReturn(Optional.of((Administrator) user));

        doThrow(new RuntimeException("Email service error")).when(emailService)
                .sendHtmlEmail(eq(email), eq("Código de recuperación"), anyString());

        assertThrows(EmailSendException.class, () -> recoveryService.sendRecoveryCode(email));

        verify(emailService, times(1)).sendHtmlEmail(eq(email), eq("Código de recuperación"), anyString());
    }

    @Test
    void testSendRecoveryCode_UserNotFound() throws Exception {

        String email = "noexiste@ejemplo.cl";

        when(administratorRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> recoveryService.sendRecoveryCode(email));

        verify(administratorRepository, times(1)).findByEmail(email);
    }

}
