package com.ufro.dci.etransparency.etransparency_api_user.services.auth;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;

import java.time.LocalDateTime;
import java.util.*;

import org.slf4j.*;
import org.springframework.stereotype.Service;

import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.EmailSendException;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity;
import com.ufro.dci.etransparency.etransparency_api_user.models.administrator.Administrator;
import com.ufro.dci.etransparency.etransparency_api_user.models.auditor.Auditor;
import com.ufro.dci.etransparency.etransparency_api_user.models.manager.Manager;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.administrator.AdministratorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.manager.ManagerRepository;
import com.ufro.dci.etransparency.etransparency_api_user.services.email.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecoveryServiceImpl implements RecoveryService {

    private static final Logger logger = LoggerFactory.getLogger(RecoveryServiceImpl.class);

    private final AdministratorRepository administratorRepository;

    private final ManagerRepository managerRepository;

    private final AuditorRepository auditorRepository;

    private final EmailService emailService;

    public String sendRecoveryCode(String email) {
        UserEntity user = findUserByEmail(email);

        String recoveryCode = UUID.randomUUID().toString().substring(0, 6);
        user.setRecoveryCode(recoveryCode);
        user.setRecoveryCodeExpiration(LocalDateTime.now().plusMinutes(15));
        String messageContent = String.format(
                """
                        Estimado(a) %s,

                        Su código de recuperación es: %s

                        Este código expirará en 15 minutos.

                        Atentamente,
                        Equipo de e-Transparencia
                        """, user.getUsername(), recoveryCode);

        try {
            emailService.sendHtmlEmail(user.getEmail(), "Código de recuperación",
                    messageContent);
            logger.info("Email sent to {} regarding password recovery", user.getEmail(),
                    user.getEmail());
        } catch (Exception e) {
            logger.error("Failed to send email to {}: {}", user.getEmail(), e.getMessage(), e);
            throw new EmailSendException(OPERATION_FAILED, FAILED_EMAIL);
        }

        return "Código de recuperación enviado";
    }

    private UserEntity findUserByEmail(String email) {
        System.out.println("Email: " + email);
        Optional<Administrator> adminOpt = administratorRepository.findByEmail(email);
        if (adminOpt.isPresent()) {
            Administrator admin = adminOpt.get();
            System.out.println("Administrador encontrado");
            return admin;
        }

        Optional<Manager> managerOpt = managerRepository.findByEmail(email);
        if (managerOpt.isPresent()) {
            Manager manager = managerOpt.get();
            System.out.println("Gestor encontrado");
            return manager;
        }

        Optional<Auditor> auditorOpt = auditorRepository.findByEmail(email);
        if (auditorOpt.isPresent()) {
            Auditor auditor = auditorOpt.get();
            System.out.println("Auditor encontrado");
            return auditor;
        }

        throw new ResourceNotFoundException(NOT_FOUND, "The user with the present email was not found");

    }

    public String validateRecoveryCode(String recoveryCode) {
        return null;
    }

    public String validateNewPassword(String password, String validationPassword) {
        return null;
    }

}
