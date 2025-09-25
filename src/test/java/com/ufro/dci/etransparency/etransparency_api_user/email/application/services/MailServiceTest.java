package com.ufro.dci.etransparency.etransparency_api_user.email.application.services;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ufro.dci.etransparency.etransparency_api_user.email.domain.models.MailMessage;
import com.ufro.dci.etransparency.etransparency_api_user.email.domain.ports.in.ComposeMailUseCase;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock
    private ComposeMailUseCase composeMailUseCase;

    @InjectMocks
    private MailService mailService;

    @Test
    void shouldSendGenericMail() {
        MailMessage message = new MailMessage();
        mailService.sendGenericMail(message);
        verify(composeMailUseCase).sendGenericMail(message);
    }

    @Test
    void shouldSendEvaluationRejectedEmail() {
        MailMessage message = new MailMessage();
        mailService.sendEvaluationRejectedEmail(message);
        verify(composeMailUseCase).sendEvaluationRejectedEmail(message);
    }

    @Test
    void shouldSendAuditEvaluationEmail() {
        MailMessage message = new MailMessage();
        mailService.sendAuditEvaluationEmail(message);
        verify(composeMailUseCase).sendAuditEvaluationEmail(message);
    }

    @Test
    void shouldSendFinishEvaluationEmail() {
        MailMessage message = new MailMessage();
        mailService.sendFinishEvaluationEmail(message);
        verify(composeMailUseCase).sendFinishEvaluationEmail(message);
    }

    @Test
    void shouldSendNewEvaluationRequestEmail() {
        MailMessage message = new MailMessage();
        mailService.sendNewEvaluationRequestEmail(message);
        verify(composeMailUseCase).sendNewEvaluationRequestEmail(message);
    }

    @Test
    void shouldSendEvidenceRejectedEmail() {
        MailMessage message = new MailMessage();
        String evaluationName = "EvaluationTest";
        mailService.sendEvidenceRejectedEmail(message, evaluationName);
        verify(composeMailUseCase).sendEvidenceRejectedEmail(message, evaluationName);
    }

    @Test
    void shouldSendAppealEvidenceEmail() {
        MailMessage message = new MailMessage();
        String evaluationName = "EvaluationTest";
        mailService.sendAppealEvidenceEmail(message, evaluationName);
        verify(composeMailUseCase).sendAppealEvidenceEmail(message, evaluationName);
    }

    @Test
    void shouldSendNewInstitutionRequestEmail() {
        MailMessage message = new MailMessage();
        mailService.sendNewInstitutionRequestEmail(message);
        verify(composeMailUseCase).sendNewInstitutionRequestEmail(message);
    }

}
