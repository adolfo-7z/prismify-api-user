package prismify.email.infrastructure.controllers.exception.custom;

public class UserPortException extends RuntimeException {

    private static final String DEFAULT_ERROR_CODE = "USER_PORT_ERROR";
    private static final String DEFAULT_ERROR_MESSAGE = "Failed to communicate with user API";

    private final String errorCode;

    public UserPortException() {
        super(DEFAULT_ERROR_MESSAGE);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public UserPortException(String message) {
        super(message);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public UserPortException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode != null ? errorCode : DEFAULT_ERROR_CODE;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
