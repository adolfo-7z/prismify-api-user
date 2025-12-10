package prismify.user.infrastructure.controllers.exception.custom;

public class InvalidRecoveryCodeException extends RuntimeException {

    private static final String DEFAULT_ERROR_CODE = "BAD_REQUEST";
    private static final String DEFAULT_ERROR_MESSAGE = "Field copy failed";

    private final String errorCode;

    public InvalidRecoveryCodeException() {
        super(DEFAULT_ERROR_MESSAGE);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public InvalidRecoveryCodeException(String message) {
        super(message);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public InvalidRecoveryCodeException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode != null ? errorCode : DEFAULT_ERROR_CODE;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
