package com.ufro.dci.etransparency.etransparency_api_user.user.application.usecases;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.UserRepository;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteUserUseCaseImpl useCase;

    @Test
    void shouldReturnSuccessMessageWhenUserIsDeleted() {
        Long userId = 42L;
        Mockito.when(userRepository.deleteById(userId)).thenReturn(true);
        String result = useCase.deleteUser(userId);
        assertThat(result).isEqualTo("User deleted");
        Mockito.verify(userRepository).deleteById(userId);
    }

    @Test
    void shouldReturnNotFoundMessageWhenUserDoesNotExist() {
        Long userId = 404L;
        Mockito.when(userRepository.deleteById(userId)).thenReturn(false);
        String result = useCase.deleteUser(userId);
        assertThat(result).isEqualTo("User with the present ID was not found");
        Mockito.verify(userRepository).deleteById(userId);
    }

}
