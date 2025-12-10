package prismify.user.infrastructure.controllers.exception.custom;

public class EmailAlreadyExistsException extends RuntimeException {

    private static final String DEFAULT_ERROR_CODE = "EMAIL_ALREADY_EXISTS";
    private static final String DEFAULT_ERROR_MESSAGE = "email is already being used";

    private final String errorCode;

    public EmailAlreadyExistsException() {
        super(DEFAULT_ERROR_MESSAGE);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public EmailAlreadyExistsException(String message) {
        super(message);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public EmailAlreadyExistsException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode != null ? errorCode : DEFAULT_ERROR_CODE;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
