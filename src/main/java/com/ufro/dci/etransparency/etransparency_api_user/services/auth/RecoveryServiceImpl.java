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
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity;
import com.ufro.dci.etransparency.etransparency_api_user.models.administrator.Administrator;
import com.ufro.dci.etransparency.etransparency_api_user.models.auditor.Auditor;
import com.ufro.dci.etransparency.etransparency_api_user.models.manager.Manager;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.administrator.AdministratorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.manager.ManagerRepository;
import com.ufro.dci.etransparency.etransparency_api_user.services.email.EmailService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecoveryServiceImpl implements RecoveryService {

    private static final Logger logger = LoggerFactory.getLogger(RecoveryServiceImpl.class);

    private final PasswordEncoder passwordEncoder;

    private final AdministratorRepository administratorRepository;

    private final ManagerRepository managerRepository;

    private final AuditorRepository auditorRepository;

    private final EmailService emailService;

    @Override
    @Transactional
    public String sendRecoveryCode(String email) {
        UserEntity user = findUserByEmail(email);

        String recoveryCode = UUID.randomUUID().toString().substring(0, 6);
        user.setRecoveryCode(recoveryCode);
        user.setRecoveryCodeExpiration(LocalDateTime.now().plusMinutes(15));
        String messageContent = String.format(
                RECOVERY_CODE_MAIL, user.getUsername(), recoveryCode);

        saveUser(user);

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

    private UserEntity findUserByEmail(String email) {
        System.out.println("Email: " + email);
        Optional<Administrator> adminOpt = administratorRepository.findByEmail(email);
        if (adminOpt.isPresent()) {
            Administrator admin = adminOpt.get();
            return admin;
        }

        Optional<Manager> managerOpt = managerRepository.findByEmail(email);
        if (managerOpt.isPresent()) {
            Manager manager = managerOpt.get();
            return manager;
        }

        Optional<Auditor> auditorOpt = auditorRepository.findByEmail(email);
        if (auditorOpt.isPresent()) {
            Auditor auditor = auditorOpt.get();
            return auditor;
        }

        throw new ResourceNotFoundException(NOT_FOUND, "The user with the present email was not found");

    }

    private void saveUser(UserEntity user) {
        if (user instanceof Administrator admin) {
            administratorRepository.save(admin);
        } else if (user instanceof Manager manager) {
            managerRepository.save(manager);
        } else if (user instanceof Auditor auditor) {
            auditorRepository.save(auditor);
        } else {
            throw new IllegalArgumentException("Unknown user type: cannot save.");
        }
    }

    @Override
    public String validateRecoveryCode(String email, String recoveryCode) {
        UserEntity user = findUserByEmail(email);

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
        UserEntity user = findUserByEmail(email);

        if (!password.equals(validationPassword)) {
            throw new InvalidPasswordException(OPERATION_FAILED, "Received passwords do not match");
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException(OPERATION_FAILED,
                    "New password can not be the same as previous password");
        }

        String encodedNewPassword = passwordEncoder.encode(password);
        user.setPassword(encodedNewPassword);
        saveUser(user);

        logger.info("Password updated successfully for user {}", user.getUsername());

        return "Password successfully updated";
    }

}
