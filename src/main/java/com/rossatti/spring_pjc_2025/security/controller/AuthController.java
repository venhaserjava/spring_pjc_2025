package com.rossatti.spring_pjc_2025.security.controller;

//import com.rossatti.spring_pjc_2025.security.jwt.JwtTokenProvider;
import com.rossatti.spring_pjc_2025.security.jwt.JwtUtil;
import com.rossatti.spring_pjc_2025.security.model.AuthRequest;
import com.rossatti.spring_pjc_2025.security.model.AuthResponse;
import com.rossatti.spring_pjc_2025.security.service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    private final AuthenticationManager authenticationManager;
//    private final JwtTokenProvider jwtTokenProvider;

    private final UserDetailsService userDetailsService;

    public AuthController(
        AuthenticationManager authenticationManager, 
//        JwtTokenProvider jwtTokenProvider,
        RefreshTokenService refreshTokenService,
        JwtUtil jwtUtil,
        UserDetailsService userDetailsService
    ) {
        this.authenticationManager = authenticationManager;
//        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
    
        SecurityContextHolder.getContext().setAuthentication(authentication);
    
        // Carrega os detalhes do usuário para gerar um token com roles
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails);
    
        return ResponseEntity.ok(new AuthResponse(token, new Date(System.currentTimeMillis() + 5 * 60 * 1000)));
    }    

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        return refreshTokenService.findByToken(refreshToken)
                .map(token -> {
                    if (token.getExpiryDate().isBefore(Instant.now())) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expirado!");
                    }
//                    String newAccessToken = jwtUtil.generateToken(token.getUser().getUsername());
                    UserDetails userDetails = userDetailsService.loadUserByUsername(token.getUser().getUsername());
                    String newAccessToken = jwtUtil.generateToken(userDetails);

                    return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido!"));
    }

}
