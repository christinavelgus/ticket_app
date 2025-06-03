package com.example.ticketbooking.service;

import com.example.ticketbooking.model.DTO.LoginResponse;
import com.example.ticketbooking.model.RefreshToken;
import com.example.ticketbooking.model.User;
import com.example.ticketbooking.repository.UserRepository;
import com.example.ticketbooking.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    public String register(String email, String password, String role) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("User with this email already exists");
        }
        String hashedPassword = passwordEncoder.encode(password);
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(hashedPassword);
        newUser.setRole(role);
        userRepository.save(newUser);
        return jwtUtil.generateToken(newUser.getId(), newUser.getEmail(), newUser.getRole());
    }

    public LoginResponse login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        String accessToken = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return new LoginResponse(accessToken, refreshToken.getToken(), user.getEmail(), user.getRole());
    }

    // Метод для отримання ролі користувача за email
    public Optional<String> getRoleByEmail(String email) {
        return userRepository.findByEmail(email).map(User::getRole);
    }
}
