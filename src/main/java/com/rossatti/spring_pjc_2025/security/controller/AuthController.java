package com.rossatti.spring_pjc_2025.security.controller;

import com.rossatti.spring_pjc_2025.security.jwt.JwtTokenProvider;
import com.rossatti.spring_pjc_2025.security.model.AuthRequest;
import com.rossatti.spring_pjc_2025.security.model.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(
        AuthenticationManager authenticationManager, 
        JwtTokenProvider jwtTokenProvider
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(request.getUsername());

        return ResponseEntity.ok(new AuthResponse(token, new Date(System.currentTimeMillis() + 5 * 60 * 1000)));
    }
}
