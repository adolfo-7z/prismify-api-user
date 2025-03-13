package com.ufro.dci.etransparency.etransparency_api_user.services.auth;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;

import java.time.LocalDateTime;
import java.util.*;

import org.slf4j.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.EmailSendException;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.InvalidPasswordException;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.InvalidRecoveryCodeException;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity;
import com.ufro.dci.etransparency.etransparency_api_user.services.auth.utils.RecoveryCommonsUtils;
import com.ufro.dci.etransparency.etransparency_api_user.services.email.EmailService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecoveryServiceImpl implements RecoveryService {

    private static final Logger logger = LoggerFactory.getLogger(RecoveryServiceImpl.class);

    private final PasswordEncoder passwordEncoder;

    private final RecoveryCommonsUtils recoveryCommonsUtils;

    private final EmailService emailService;

    @Override
    @Transactional
    public String sendRecoveryCode(String email) {
        UserEntity user = recoveryCommonsUtils.findUserByEmail(email);

        String recoveryCode = UUID.randomUUID().toString().substring(0, 6);
        user.setRecoveryCode(recoveryCode);
        user.setRecoveryCodeExpiration(LocalDateTime.now().plusMinutes(15));
        String messageContent = String.format(
                RECOVERY_CODE_MAIL, user.getUsername(), recoveryCode);

        recoveryCommonsUtils.saveUser(user);

        try {
            emailService.sendHtmlEmail(user.getEmail(), "Código de recuperación",
                    messageContent);
            logger.info("Email sent to {} regarding password recovery", user.getEmail(),
                    user.getEmail());
        } catch (Exception e) {
            logger.error("Failed to send email to {}: {}", user.getEmail(), e.getMessage(), e);
            throw new EmailSendException(OPERATION_FAILED, FAILED_EMAIL);
        }

        return "Recovery code sent";
    }

    @Override
    public String validateRecoveryCode(String email, String recoveryCode) {
        UserEntity user = recoveryCommonsUtils.findUserByEmail(email);

        String userRecoveryCode = user.getRecoveryCode();
        if (userRecoveryCode == null || userRecoveryCode.isEmpty()) {
            throw new InvalidRecoveryCodeException(OPERATION_FAILED, "No recovery code found for this user.");
        }

        LocalDateTime expiration = user.getRecoveryCodeExpiration();
        if (expiration == null || expiration.isBefore(LocalDateTime.now())) {
            throw new InvalidRecoveryCodeException(OPERATION_FAILED, "The recovery code has expired.");
        }

        if (!userRecoveryCode.equals(recoveryCode)) {
            throw new InvalidRecoveryCodeException(OPERATION_FAILED, "Invalid recovery code provided.");
        }

        return "Recovery code successfully validated";
    }

    @Override
    @Transactional
    public String validateNewPassword(String email, String password, String validationPassword) {
        UserEntity user = recoveryCommonsUtils.findUserByEmail(email);

        if (!password.equals(validationPassword)) {
            throw new InvalidPasswordException(OPERATION_FAILED, "Received passwords do not match");
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException(OPERATION_FAILED,
                    "New password can not be the same as previous password");
        }

        String encodedNewPassword = passwordEncoder.encode(password);
        user.setPassword(encodedNewPassword);
        recoveryCommonsUtils.saveUser(user);

        logger.info("Password updated successfully for user {}", user.getUsername());

        return "Password successfully updated";
    }

}
