package prismify.user.infrastructure.controllers.exception.custom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UserEmailPortExceptionTest {

    @Test
    void shouldCreateExceptionWithDefaultConstructor() {
        UserEmailPortException exception = new UserEmailPortException();
        assertEquals("Failed to communicate with mail API", exception.getMessage());
        assertEquals("MAIL_PORT_ERROR", exception.getErrorCode());
    }

    @Test
    void shouldCreateExceptionWithCustomMessage() {
        String customMessage = "Failed to communicate with external API";
        UserEmailPortException exception = new UserEmailPortException(customMessage);
        assertEquals(customMessage, exception.getMessage());
        assertEquals("MAIL_PORT_ERROR", exception.getErrorCode());
    }

    @Test
    void shouldCreateExceptionWithCustomErrorCodeAndMessage() {
        String customMessage = "Rejected transfer";
        String customCode = "MAPPER_ERROR_001";
        UserEmailPortException exception = new UserEmailPortException(customCode, customMessage);
        assertEquals(customMessage, exception.getMessage());
        assertEquals(customCode, exception.getErrorCode());
    }

    @Test
    void shouldFallbackToDefaultCodeWhenErrorCodeIsNull() {
        UserEmailPortException exception = new UserEmailPortException(null, "Null code test");
        assertEquals("Null code test", exception.getMessage());
        assertEquals("MAIL_PORT_ERROR", exception.getErrorCode());
    }
    
}
