package com.rossatti.spring_pjc_2025.security.service;

import com.rossatti.spring_pjc_2025.security.model.User;
import com.rossatti.spring_pjc_2025.security.model.RefreshToken;
import com.rossatti.spring_pjc_2025.security.repository.UserRepository;
import com.rossatti.spring_pjc_2025.security.repository.RefreshTokenRepository;

import java.util.UUID;
import java.time.Instant;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class RefreshTokenService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(
        UserRepository userRepository,
        RefreshTokenRepository refreshTokenRepository
    ) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(String username) {

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString()); // gera token aleatório
        refreshToken.setExpiryDate(Instant.now().plusSeconds(604800)); // 7 dias

        return refreshTokenRepository.save(refreshToken);
        
    }
}    

