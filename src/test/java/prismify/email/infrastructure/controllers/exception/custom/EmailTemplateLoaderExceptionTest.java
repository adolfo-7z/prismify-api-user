package prismify.email.infrastructure.controllers.exception.custom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EmailTemplateLoaderExceptionTest {

    @Test
    void shouldUseDefaultConstructor() {
        EmailTemplateLoaderException exception = new EmailTemplateLoaderException();
        assertEquals("EMAIL_TEMPLATE_ERROR", exception.getErrorCode());
        assertEquals("Failed to load email templates", exception.getMessage());
    }

    @Test
    void shouldUseMessageConstructor() {
        String message = "User service down";
        EmailTemplateLoaderException exception = new EmailTemplateLoaderException(message);
        assertEquals("EMAIL_TEMPLATE_ERROR", exception.getErrorCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    void shouldUseCustomCodeAndMessageConstructor() {
        String errorCode = "CUSTOM_ERROR";
        String message = "The user with ID 42 was not found in the database.";
        EmailTemplateLoaderException exception = new EmailTemplateLoaderException(errorCode, message);
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    void shouldFallbackToDefaultErrorCodeWhenNullPassed() {
        String message = "Null code provided";
        EmailTemplateLoaderException exception = new EmailTemplateLoaderException(null, message);
        assertEquals("EMAIL_TEMPLATE_ERROR", exception.getErrorCode());
        assertEquals(message, exception.getMessage());
    }

}
