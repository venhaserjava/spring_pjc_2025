package com.rossatti.spring_pjc_2025.security.controller;

import java.util.Map;
import java.util.UUID;
import java.util.Date;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rossatti.spring_pjc_2025.security.jwt.JwtUtil;
import com.rossatti.spring_pjc_2025.security.model.RefreshToken;
import com.rossatti.spring_pjc_2025.security.model.User;
import com.rossatti.spring_pjc_2025.security.repository.UserRepository;
import com.rossatti.spring_pjc_2025.security.service.RefreshTokenService;

import com.rossatti.spring_pjc_2025.security.dtos.request.AuthRequest;
import com.rossatti.spring_pjc_2025.security.dtos.response.AuthResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    public AuthController(
        JwtUtil jwtUtil,
        UserRepository userRepository,
        UserDetailsService userDetailsService,
        RefreshTokenService refreshTokenService,
        AuthenticationManager authenticationManager 
    ) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        String accessToken = jwtUtil.generateToken(userDetails);
        Date expiration = new Date(System.currentTimeMillis() + 5 * 60 * 1000);

        var userName = request.getUsername();
        User user = userRepository.findByUsername(userName)        
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String rawRefreshToken = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(rawRefreshToken);
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));

        refreshToken = refreshTokenService.createRefreshToken(request.getUsername());

        return ResponseEntity.ok(AuthResponse
                .builder()
                .token(accessToken) 
                .expiresAt(expiration)
                .refreshToken(refreshToken.getToken())
                .build()
        );    
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        return refreshTokenService.findByToken(refreshToken)
                .map(token -> {
                    if (token.getExpiryDate().isBefore(Instant.now())) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expirado!");
                    }
                    UserDetails userDetails = userDetailsService.loadUserByUsername(token.getUser().getUsername());
                    String newAccessToken = jwtUtil.generateToken(userDetails);

                    return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido!"));
    }

}
