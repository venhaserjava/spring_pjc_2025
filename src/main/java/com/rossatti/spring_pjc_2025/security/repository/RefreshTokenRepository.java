package com.rossatti.spring_pjc_2025.security.repository;

import com.rossatti.spring_pjc_2025.security.jwt.RefreshToken;
import com.rossatti.spring_pjc_2025.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}
