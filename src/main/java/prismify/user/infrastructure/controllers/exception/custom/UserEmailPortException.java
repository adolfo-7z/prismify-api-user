package prismify.user.infrastructure.controllers.exception.custom;

public class UserEmailPortException extends RuntimeException {

    private static final String DEFAULT_ERROR_CODE = "MAIL_PORT_ERROR";
    private static final String DEFAULT_ERROR_MESSAGE = "Failed to communicate with mail API";

    private final String errorCode;

    public UserEmailPortException() {
        super(DEFAULT_ERROR_MESSAGE);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public UserEmailPortException(String message) {
        super(message);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public UserEmailPortException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode != null ? errorCode : DEFAULT_ERROR_CODE;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
