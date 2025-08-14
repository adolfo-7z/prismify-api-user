package com.ufro.dci.etransparency.etransparency_api_user.services.auth;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ufro.dci.etransparency.etransparency_api_user.TransparencyApiUserApplication;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.*;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity;
import com.ufro.dci.etransparency.etransparency_api_user.models.administrator.Administrator;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.administrator.AdministratorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.manager.ManagerRepository;
import com.ufro.dci.etransparency.etransparency_api_user.services.auth.utils.RecoveryCommonsUtils;
import com.ufro.dci.etransparency.etransparency_api_user.services.email.EmailService;

@SpringBootTest(classes = TransparencyApiUserApplication.class)
@AutoConfigureMockMvc
public class RecoveryServiceImplTest {

    @Autowired
    private RecoveryService recoveryService;

    @MockitoBean
    private AdministratorRepository administratorRepository;

    @MockitoBean
    private ManagerRepository managerRepository;

    @MockitoBean
    private AuditorRepository auditorRepository;

    @MockitoBean
    private EmailService emailService;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private RecoveryCommonsUtils recoveryCommonsUtils;

    @Test
    void testSendRecoveryCode_Success() throws Exception {
        String email = "prueba@ejemplo.cl";
        UserEntity user = new Administrator();
        user.setEmail(email);
        user.setUsername("Juan Fulano");

        when(recoveryCommonsUtils.findUserByEmail(email)).thenReturn(user);
        doNothing().when(recoveryCommonsUtils).saveUser(user);
        doNothing().when(emailService).sendHtmlEmail(eq(email), eq("Código de recuperación"), anyString());

        String response = recoveryService.sendRecoveryCode(email);

        verify(recoveryCommonsUtils, times(1)).findUserByEmail(email);
        verify(recoveryCommonsUtils, times(1)).saveUser(user);
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

        when(recoveryCommonsUtils.findUserByEmail(email)).thenReturn(user);
        doNothing().when(recoveryCommonsUtils).saveUser(user);
        doThrow(new RuntimeException("Email service error")).when(emailService)
                .sendHtmlEmail(eq(email), eq("Código de recuperación"), anyString());

        assertThrows(EmailSendException.class, () -> recoveryService.sendRecoveryCode(email));

        verify(recoveryCommonsUtils, times(1)).findUserByEmail(email);
        verify(recoveryCommonsUtils, times(1)).saveUser(user);
        verify(emailService, times(1)).sendHtmlEmail(eq(email), eq("Código de recuperación"), anyString());
    }

    @Test
    void testSendRecoveryCode_UserNotFound() throws Exception {
        String email = "noexiste@ejemplo.cl";

        when(recoveryCommonsUtils.findUserByEmail(email))
                .thenThrow(new ResourceNotFoundException(OPERATION_FAILED, "User not found"));

        assertThrows(ResourceNotFoundException.class, () -> recoveryService.sendRecoveryCode(email));

        verify(recoveryCommonsUtils, times(1)).findUserByEmail(email);
        verifyNoInteractions(emailService);
    }

    @Test
    void validateRecoveryCode_ShouldReturnSuccess_WhenCodeIsValidAndNotExpired() {
        String email = "prueba@ejemplo.cl";
        String recoveryCode = "123456";

        UserEntity mockUser = mock(UserEntity.class);
        when(recoveryCommonsUtils.findUserByEmail(email)).thenReturn(mockUser);
        when(mockUser.getRecoveryCode()).thenReturn(recoveryCode);
        when(mockUser.getRecoveryCodeExpiration()).thenReturn(LocalDateTime.now().plusMinutes(10));

        String result = recoveryService.validateRecoveryCode(email, recoveryCode);

        assertEquals("Recovery code successfully validated", result);
    }

    @Test
    void validateRecoveryCode_ShouldThrowException_WhenNoRecoveryCodeFound() {
        String email = "prueba@ejemplo.cl";
        String recoveryCode = "123456";

        UserEntity mockUser = mock(UserEntity.class);
        when(recoveryCommonsUtils.findUserByEmail(email)).thenReturn(mockUser);
        when(mockUser.getRecoveryCode()).thenReturn(null);

        InvalidRecoveryCodeException ex = assertThrows(InvalidRecoveryCodeException.class,
                () -> recoveryService.validateRecoveryCode(email, recoveryCode));

        assertEquals(OPERATION_FAILED, ex.getErrorCode());
        assertEquals("No recovery code found for this user.", ex.getMessage());
    }

    @Test
    void validateRecoveryCode_ShouldThrowException_WhenRecoveryCodeIsExpired() {
        String email = "prueba@ejemplo.cl";
        String recoveryCode = "123456";

        UserEntity mockUser = mock(UserEntity.class);
        when(recoveryCommonsUtils.findUserByEmail(email)).thenReturn(mockUser);
        when(mockUser.getRecoveryCode()).thenReturn(recoveryCode);
        when(mockUser.getRecoveryCodeExpiration()).thenReturn(LocalDateTime.now().minusMinutes(10));

        InvalidRecoveryCodeException ex = assertThrows(InvalidRecoveryCodeException.class,
                () -> recoveryService.validateRecoveryCode(email, recoveryCode));

        assertEquals(OPERATION_FAILED, ex.getErrorCode());
        assertEquals("The recovery code has expired.", ex.getMessage());
    }

    @Test
    void validateRecoveryCode_ShouldThrowException_WhenRecoveryCodeDoesNotMatch() {
        String email = "prueba@ejemplo.cl";
        String recoveryCode = "123456";

        UserEntity mockUser = mock(UserEntity.class);
        when(recoveryCommonsUtils.findUserByEmail(email)).thenReturn(mockUser);
        when(mockUser.getRecoveryCode()).thenReturn("654321");
        when(mockUser.getRecoveryCodeExpiration()).thenReturn(LocalDateTime.now().plusMinutes(10));

        InvalidRecoveryCodeException ex = assertThrows(InvalidRecoveryCodeException.class,
                () -> recoveryService.validateRecoveryCode(email, recoveryCode));

        assertEquals(OPERATION_FAILED, ex.getErrorCode());
        assertEquals("Invalid recovery code provided.", ex.getMessage());
    }

    @Test
    void validateNewPassword_ShouldUpdatePasswordSuccessfully() {
        String email = "prueba@ejemplo.cl";
        String newPassword = "Contrasenia123%";
        String validationPassword = "Contrasenia123%";

        UserEntity user = new Administrator();
        user.setEmail(email);
        user.setPassword("OldEncodedPassword");
        user.setUsername("UsuarioPrueba");

        when(recoveryCommonsUtils.findUserByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(newPassword, user.getPassword())).thenReturn(false);
        when(passwordEncoder.encode(newPassword)).thenReturn("EncodedNewPassword");

        String result = recoveryService.validateNewPassword(email, newPassword, validationPassword);

        assertEquals("Password successfully updated", result);
        assertEquals("EncodedNewPassword", user.getPassword());
        verify(recoveryCommonsUtils).saveUser(user);
        verify(recoveryCommonsUtils).findUserByEmail(email);
    }

    @Test
    void validateNewPassword_ShouldThrowException_WhenPasswordsDoNotMatch() {
        String email = "prueba@ejemplo.cl";
        String password = "Contrasenia123%";
        String validationPassword = "123%Contrasenia";

        UserEntity user = new Administrator();
        user.setEmail(email);

        when(recoveryCommonsUtils.findUserByEmail(email)).thenReturn(user);

        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class,
                () -> recoveryService.validateNewPassword(email, password, validationPassword));

        assertEquals("Received passwords do not match", exception.getMessage());
        verify(recoveryCommonsUtils).findUserByEmail(email);
        verify(recoveryCommonsUtils, never()).saveUser(any());
    }

    @Test
    void validateNewPassword_ShouldThrowException_WhenPasswordIsSameAsOld() {
        String email = "prueba@ejemplo.cl";
        String password = "Contrasenia123%";

        UserEntity user = new Administrator();
        user.setEmail(email);
        user.setPassword("EncodedOldPassword");

        when(recoveryCommonsUtils.findUserByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class,
                () -> recoveryService.validateNewPassword(email, password, password));

        assertEquals("New password can not be the same as previous password", exception.getMessage());
        verify(recoveryCommonsUtils).findUserByEmail(email);
        verify(recoveryCommonsUtils, never()).saveUser(any());
    }

    @Test
    void validateNewPassword_ShouldThrowException_WhenUserNotFound() {
        String email = "desconocido@ejemplo.cl";
        String password = "Contrasenia123%";

        when(recoveryCommonsUtils.findUserByEmail(email))
                .thenThrow(new ResourceNotFoundException(OPERATION_FAILED, "User not found"));


        assertThrows(ResourceNotFoundException.class,
                () -> recoveryService.validateNewPassword(email, password, password));

        verify(recoveryCommonsUtils).findUserByEmail(email);
        verify(recoveryCommonsUtils, never()).saveUser(any());
    }

}
