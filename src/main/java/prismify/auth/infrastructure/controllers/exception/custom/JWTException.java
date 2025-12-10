package prismify.auth.infrastructure.controllers.exception.custom;

public class JWTException extends RuntimeException {

    private static final String DEFAULT_ERROR_CODE = "JWT_ERROR";
    private static final String DEFAULT_ERROR_MESSAGE = "Error with JSON web token";

    private final String errorCode;

    public JWTException() {
        super(DEFAULT_ERROR_MESSAGE);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public JWTException(String message) {
        super(message);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public JWTException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode != null ? errorCode : DEFAULT_ERROR_CODE;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
