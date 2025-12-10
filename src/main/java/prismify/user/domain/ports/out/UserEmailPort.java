package prismify.user.domain.ports.out;

public interface UserEmailPort {
    void sendRecoveryCodeEmail(String to, String code);

    void sendNewPasswordAlert(String to);
}
