package com.example.ticketbooking.model.DTO;

public class RegisterRequest {
    private String email;
    private String password;
    private String role;

    // Геттер і сеттер для email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Геттер і сеттер для password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Геттер і сеттер для role
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}