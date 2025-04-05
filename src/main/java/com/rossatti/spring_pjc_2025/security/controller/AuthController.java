package com.rossatti.spring_pjc_2025.security.controller;

import com.rossatti.spring_pjc_2025.security.dtos.request.AuthRequest;
import com.rossatti.spring_pjc_2025.security.dtos.response.AuthResponse;
//import com.rossatti.spring_pjc_2025.security.jwt.JwtTokenProvider;
import com.rossatti.spring_pjc_2025.security.jwt.JwtUtil;
//import com.rossatti.spring_pjc_2025.security.model.AuthResponse;
import com.rossatti.spring_pjc_2025.security.model.RefreshToken;
import com.rossatti.spring_pjc_2025.security.model.User;
import com.rossatti.spring_pjc_2025.security.repository.UserRepository;
import com.rossatti.spring_pjc_2025.security.service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

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
        UserDetailsService userDetailsService,
        UserRepository userRepository
    ) {
        this.authenticationManager = authenticationManager;
//        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
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

        // Busca a entidade real de usuário
//        Optional<User> user = userRepository.findByUsername(request.getUsername());
//        var user = new User();
        var userName = request.getUsername();
        User user = userRepository.findByUsername(userName)        
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        // Cria e salva o refresh token
        String rawRefreshToken = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(rawRefreshToken);
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));

        refreshToken = refreshTokenService.createRefreshToken(request.getUsername());
//        return ResponseEntity.ok(new AuthResponse(accessToken, expiration, refreshToken.getToken()));
        return ResponseEntity.ok(AuthResponse
                .builder()
                .token(accessToken) // atributo repetido, depois eu ajusto.
//                .accessToken(accessToken)
                .expiresAt(expiration)
                .refreshToken(refreshToken.getToken())
                .build()
        );    
    }

/* versão anterior qeu não gera e nem salva o refreshtoken
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
*/

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
