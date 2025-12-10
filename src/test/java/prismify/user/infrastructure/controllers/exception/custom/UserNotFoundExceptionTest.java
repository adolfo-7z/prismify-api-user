package prismify.user.infrastructure.controllers.exception.custom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UserNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithDefaultConstructor() {
        UserNotFoundException exception = new UserNotFoundException();
        assertEquals("User was not found", exception.getMessage());
        assertEquals("USER_NOT_FOUND", exception.getErrorCode());
    }

    @Test
    void shouldCreateExceptionWithCustomMessage() {
        String message = "User with ID 42 no longer exists";
        UserNotFoundException exception = new UserNotFoundException(message);
        assertEquals(message, exception.getMessage());
        assertEquals("USER_NOT_FOUND", exception.getErrorCode());
    }

    @Test
    void shouldCreateExceptionWithCustomErrorCodeAndMessage() {
        String customCode = "USER_ERR_404";
        String message = "User vanished not found";
        UserNotFoundException exception = new UserNotFoundException(customCode, message);
        assertEquals(message, exception.getMessage());
        assertEquals(customCode, exception.getErrorCode());
    }

    @Test
    void shouldUseDefaultErrorCodeIfNullProvided() {
        UserNotFoundException exception = new UserNotFoundException(null, "No user detected");
        assertEquals("No user detected", exception.getMessage());
        assertEquals("USER_NOT_FOUND", exception.getErrorCode());
    }

}
