package com.ufro.dci.etransparency.etransparency_api_user.email.application.services;

import com.ufro.dci.etransparency.etransparency_api_user.email.domain.models.MailMessage;
import com.ufro.dci.etransparency.etransparency_api_user.email.domain.ports.in.ComposeMailUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MailService implements ComposeMailUseCase {

    private final ComposeMailUseCase composeMailUseCase;

    @Override
    public void sendGenericMail(MailMessage messgae) {
        composeMailUseCase.sendGenericMail(messgae);
    }

    @Override
    public void sendEvaluationRejectedEmail(MailMessage message) {
        composeMailUseCase.sendEvaluationRejectedEmail(message);
    }

    @Override
    public void sendAuditEvaluationEmail(MailMessage message) {
        composeMailUseCase.sendAuditEvaluationEmail(message);
    }

    @Override
    public void sendFinishEvaluationEmail(MailMessage message) {
        composeMailUseCase.sendFinishEvaluationEmail(message);
    }

    @Override
    public void sendNewEvaluationRequestEmail(MailMessage message) {
        composeMailUseCase.sendNewEvaluationRequestEmail(message);
    }

    @Override
    public void sendEvidenceRejectedEmail(MailMessage message) {
        composeMailUseCase.sendEvidenceRejectedEmail(message);
    }

    @Override
    public void sendAppealEvidenceEmail(MailMessage message) {
        composeMailUseCase.sendAppealEvidenceEmail(message);
    }

    @Override
    public void sendNewInstitutionRequestEmail(MailMessage message) {
        composeMailUseCase.sendNewInstitutionRequestEmail(message);
    }

}
