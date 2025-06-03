package com.example.ticketbooking.Service;

import com.example.ticketbooking.security.JwtUtil;
import com.example.ticketbooking.model.User;
import com.example.ticketbooking.repository.UserRepository;
import com.example.ticketbooking.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void register_WhenUserDoesNotExist_ShouldRegisterSuccessfully() {
        // Arrange
        String email = "new@example.com";
        String password = "password123";
        String role = "USER";

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn("hashedPassword");

        doReturn("mockedJwt").when(jwtUtil).generateToken(any(), eq(email), eq(role));

        // Act
        String token = userService.register(email, password, role);

        // Assert
        assertNotNull(token);
        assertEquals("mockedJwt", token);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void register_WhenUserAlreadyExists_ShouldThrowException() {
        // Arrange
        String email = "existing@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.register(email, "password", "USER"));

        assertEquals("User with this email already exists", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void register_ShouldCallPasswordEncoderAndSave() {
        // Arrange
        String email = "test@example.com";
        String password = "test123";
        String role = "USER";

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn("hashedPass");

        doReturn("token").when(jwtUtil).generateToken(any(), eq(email), eq(role));

        // Act
        userService.register(email, password, role);

        // Assert
        verify(passwordEncoder, times(1)).encode(password);
        verify(userRepository, times(1)).save(any(User.class));
    }
}
