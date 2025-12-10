package prismify.user.application.support.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.validation.ConstraintValidatorContext;
import prismify.user.domain.ports.out.UserRepository;

class UniqueUsernameValidatorTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ConstraintValidatorContext context;

    private UniqueUsernameValidator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new UniqueUsernameValidator(userRepository);
    }

    @Test
    void whenUsernameDoesNotExist_thenValidationSucceeds() {
        String username = "juan";
        when(userRepository.existsByUsername(username)).thenReturn(false);
        boolean result = validator.isValid(username, context);
        assertTrue(result);
        verify(userRepository).existsByUsername(username);
    }

    @Test
    void whenUsernameExists_thenValidationFails() {
        String username = "juan";
        when(userRepository.existsByUsername(username)).thenReturn(true);
        boolean result = validator.isValid(username, context);
        assertFalse(result);
        verify(userRepository).existsByUsername(username);
    }

    @Test
    void whenUsernameIsNull_thenValidationReturnsTrue() {
        when(userRepository.existsByUsername(null)).thenReturn(false);
        boolean result = validator.isValid(null, context);
        assertTrue(result);
        verify(userRepository).existsByUsername(null);
    }

}
