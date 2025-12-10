package prismify.user.infrastructure.controllers.exception.custom;

public class UsernameAlreadyExistsException extends RuntimeException {

    private static final String DEFAULT_ERROR_CODE = "USERNAME_ALREADY_EXISTS";
    private static final String DEFAULT_ERROR_MESSAGE = "Username is already being used";

    private final String errorCode;

    public UsernameAlreadyExistsException() {
        super(DEFAULT_ERROR_MESSAGE);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public UsernameAlreadyExistsException(String message) {
        super(message);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public UsernameAlreadyExistsException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode != null ? errorCode : DEFAULT_ERROR_CODE;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
