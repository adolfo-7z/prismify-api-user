package com.ufro.dci.etransparency.etransparency_api_user.user.application.usecases;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ufro.dci.etransparency.etransparency_api_user.user.application.support.PartialUpdateMapper;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.UserRepository;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateUserUseCaseImpl useCase;

    @Test
    void shouldUpdateUserWithNonNullFields() {
        Long userId = 33L;
        User existingUser = new User();
        existingUser.setUsername("before");
        User updateData = new User();
        updateData.setUsername("after");
        Mockito.when(userRepository.findById(userId)).thenReturn(existingUser);
        try (MockedStatic<PartialUpdateMapper> utilities = Mockito.mockStatic(PartialUpdateMapper.class)) {
            User result = useCase.updateUser(userId, updateData);
            utilities.verify(() -> PartialUpdateMapper.copyNonNullFields(updateData, existingUser));
            Mockito.verify(userRepository).update(existingUser);
            assertThat(result).isEqualTo(existingUser);
        }
    }

    @Test
    void shouldToggleUserStatusFromActiveToInactive() {
        Long userId = 44L;
        User user = new User();
        user.setActive(true);
        Mockito.when(userRepository.findById(userId)).thenReturn(user);
        String result = useCase.toggleUserStatus(userId);
        assertThat(user.isActive()).isFalse();
        assertThat(result).isEqualTo("User deactivated");
        Mockito.verify(userRepository).update(user);
    }

    @Test
    void shouldToggleUserStatusFromInactiveToActive() {
        Long userId = 45L;
        User user = new User();
        user.setActive(false);
        Mockito.when(userRepository.findById(userId)).thenReturn(user);
        String result = useCase.toggleUserStatus(userId);
        assertThat(user.isActive()).isTrue();
        assertThat(result).isEqualTo("User activated");
        Mockito.verify(userRepository).update(user);
    }

    @Test
    void shouldIncrementAuditsPerformed() {
        Long userId = 66L;
        User user = new User();
        user.setAuditsPerformed(3L);
        Mockito.when(userRepository.findById(userId)).thenReturn(user);
        useCase.incrementAuditsPerformed(userId);
        assertThat(user.getAuditsPerformed()).isEqualTo(4L);
        Mockito.verify(userRepository).update(user);
    }

}
