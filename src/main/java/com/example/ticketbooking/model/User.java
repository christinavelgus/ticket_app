package com.example.ticketbooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.util.List;
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String role = "USER"; // за замовчуванням роль USER

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    // Геттер і сеттер для id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
