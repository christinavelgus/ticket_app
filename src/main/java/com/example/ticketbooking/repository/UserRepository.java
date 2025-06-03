package com.example.ticketbooking.repository;

import com.example.ticketbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Метод для пошуку користувача за email
    Optional<User> findByEmail(String email);

    // Перевірка чи існує користувач з таким email
    boolean existsByEmail(String email);
}
