package com.example.ticketbooking.controller;

import com.example.ticketbooking.model.DTO.LoginRequest;
import com.example.ticketbooking.model.DTO.RefreshTokenRequest;
import com.example.ticketbooking.model.DTO.RegisterRequest;
import com.example.ticketbooking.security.JwtUtil;
import com.example.ticketbooking.model.RefreshToken;
import com.example.ticketbooking.model.User;
import com.example.ticketbooking.service.RefreshTokenService;
import com.example.ticketbooking.service.TokenBlacklistService;
import com.example.ticketbooking.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;
    private final TokenBlacklistService tokenBlacklistService;

    public AuthController(UserService userService, JwtUtil jwtUtil, TokenBlacklistService tokenBlacklistService, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.tokenBlacklistService = tokenBlacklistService;
        this.refreshTokenService =  refreshTokenService;
    }


    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String roleToAssign = "USER";
        if (authentication != null && authentication.isAuthenticated()) {
            boolean isAdmin = authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

            if (isAdmin)
                roleToAssign = request.getRole();
        }

        // Реєстрація користувача з визначеною роллю
        String token = userService.register(request.getEmail(), request.getPassword(), roleToAssign);

        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        var response = userService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (!refreshTokenService.verifyExpiration(refreshToken)) {
            return ResponseEntity.status(401).body("Refresh token expired");
        }

        var user = refreshToken.getUser();
        var newAccessToken = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());
        return ResponseEntity.ok(Collections.singletonMap("accessToken", newAccessToken));
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkToken(@AuthenticationPrincipal User user) {
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Видаляємо "Bearer "
            tokenBlacklistService.addToBlacklist(token);
            return ResponseEntity.ok(Collections.singletonMap("message", "Logged out successfully"));
        }
        return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Invalid token"));
    }

    @GetMapping("/getRole")
    public ResponseEntity<?> getRole(@RequestParam String email) {
        String userEmail = email.replace("\"", "").trim();

        return userService.getRoleByEmail(userEmail)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
