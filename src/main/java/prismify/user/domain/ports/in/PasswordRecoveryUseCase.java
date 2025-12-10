package prismify.user.domain.ports.in;

public interface PasswordRecoveryUseCase {

    void sendRecoveryCode(String email);

    void validateRecoveryCode(String email, String recoveryCode);

    void validateNewPassword(String email, String password, String validationPassword);

}
