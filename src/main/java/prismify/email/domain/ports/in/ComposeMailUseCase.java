package prismify.email.domain.ports.in;

import prismify.email.domain.models.MailMessage;

public interface ComposeMailUseCase {
    void sendGenericMail(MailMessage message);

    void sendEvaluationRejectedEmail(MailMessage message);

    void sendAuditEvaluationEmail(MailMessage message);

    void sendFinishEvaluationEmail(MailMessage message);

    void sendNewEvaluationRequestEmail(MailMessage message);

    void sendEvidenceRejectedEmail(MailMessage message, String evaluationName);

    void sendAppealEvidenceEmail(MailMessage message, String evaluationName);

    void sendNewInstitutionRequestEmail(MailMessage message);

    void sendRecoveryCodeEmail(MailMessage message, String code);

    void sendNewPasswordAlert(MailMessage message);

    void sendAuditorAssigmentEmail(MailMessage message, String newEvaluation);
}
