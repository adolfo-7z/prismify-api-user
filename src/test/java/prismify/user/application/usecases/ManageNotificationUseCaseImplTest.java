package prismify.user.application.usecases;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import prismify.user.domain.models.*;
import prismify.user.domain.ports.out.UserRepository;

@ExtendWith(MockitoExtension.class)
class ManageNotificationUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ManageNotificationUseCaseImpl useCase;

    @Test
    void createNotification_shouldAddNotificationToUser() {
        User user = new User();
        user.setNotifications(new ArrayList<>());
        when(userRepository.findById(1L)).thenReturn(user);
        useCase.createNotification(1L, "Hello!");
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User saved = captor.getValue();
        assertThat(saved.getNotifications()).hasSize(1);
        assertThat(saved.getNotifications().get(0).getMessage()).isEqualTo("Hello!");
    }

    @Test
    void createNotification_shouldTrimToMax10Notifications() {
        User user = new User();
        List<Notification> existing = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Notification n = new Notification();
            n.setId((long) i);
            n.setMessage("n" + i);
            existing.add(n);
        }
        user.setNotifications(existing);
        when(userRepository.findById(5L)).thenReturn(user);
        useCase.createNotification(5L, "new");
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User saved = captor.getValue();
        assertThat(saved.getNotifications()).hasSize(10);
        assertThat(saved.getNotifications().get(0).getMessage()).isEqualTo("n1");
        assertThat(saved.getNotifications().get(9).getMessage()).isEqualTo("new");
    }

    @Test
    void createAdminNotification_shouldAddNotificationToAdmin() {
        User admin = new User();
        admin.setNotifications(new ArrayList<>());
        when(userRepository.findByRole(Role.ADMIN)).thenReturn(admin);
        useCase.createAdminNotification("Admin alert!");
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User saved = captor.getValue();
        assertThat(saved.getNotifications()).hasSize(1);
        assertThat(saved.getNotifications().get(0).getMessage()).isEqualTo("Admin alert!");
    }

    @Test
    void getNotifications_shouldReturnUserNotifications() {
        User user = new User();
        List<Notification> list = List.of(new Notification(), new Notification());
        user.setNotifications(list);
        when(userRepository.findById(7L)).thenReturn(user);
        List<Notification> result = useCase.getNotifications(7L);
        assertThat(result)
                .hasSize(2)
                .isEqualTo(list);
    }

    @Test
    void removeNotification_shouldRemoveCorrectNotification() {
        User user = new User();
        Notification n1 = new Notification();
        n1.setId(10L);
        Notification n2 = new Notification();
        n2.setId(20L);
        user.setNotifications(new ArrayList<>(List.of(n1, n2)));
        when(userRepository.findById(3L)).thenReturn(user);
        useCase.removeNotification(3L, 10L);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).update(captor.capture());
        User updated = captor.getValue();
        assertThat(updated.getNotifications()).hasSize(1);
        assertThat(updated.getNotifications().get(0).getId()).isEqualTo(20L);
    }

    @Test
    void clearNotifications_shouldRemoveAllNotifications() {
        User user = new User();
        user.setNotifications(new ArrayList<>(List.of(new Notification(), new Notification())));
        when(userRepository.findById(9L)).thenReturn(user);
        useCase.clearNotifications(9L);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User saved = captor.getValue();
        assertThat(saved.getNotifications()).isEmpty();
    }

}
