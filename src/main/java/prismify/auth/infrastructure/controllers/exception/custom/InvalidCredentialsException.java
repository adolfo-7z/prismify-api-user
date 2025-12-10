package prismify.auth.infrastructure.controllers.exception.custom;

public class InvalidCredentialsException extends RuntimeException {

    private static final String DEFAULT_ERROR_CODE = "INVALID_CREDENTIALS";
    private static final String DEFAULT_ERROR_MESSAGE = "Invalid username or password";

    private final String errorCode;

    public InvalidCredentialsException() {
        super(DEFAULT_ERROR_MESSAGE);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public InvalidCredentialsException(String message) {
        super(message);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public InvalidCredentialsException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode != null ? errorCode : DEFAULT_ERROR_CODE;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
