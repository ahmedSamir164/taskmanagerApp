package com.ahmed.tasks_manager.Service;
import com.ahmed.tasks_manager.Entities.Role;
import com.ahmed.tasks_manager.Entities.User;
import com.ahmed.tasks_manager.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");

        Role role = new Role();
        role.setName("ROLE_USER");
        user.setRoles(Set.of(role));


        when(userRepository.findByUsernameOrEmail("test@example.com", "test@example.com"))
                .thenReturn(Optional.of(user));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("test@example.com");

        assertNotNull(userDetails);
        assertEquals("test@example.com", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        verify(userRepository, times(1)).findByUsernameOrEmail("test@example.com", "test@example.com");
        System.out.println(userDetails.getAuthorities());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByUsernameOrEmail("unknown@example.com", "unknown@example.com"))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("unknown@example.com");
        });

        String expectedMessage = "User not found with username or email: unknown@example.com";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepository, times(1)).findByUsernameOrEmail("unknown@example.com", "unknown@example.com");
    }
}

