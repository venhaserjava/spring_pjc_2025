package com.rossatti.spring_pjc_2025.security.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /* Os dados de usuarios fixos aqui são só para efeitos didaticos */        
        if (!username.equals("admin")) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }

        return new User(
                "admin",
                passwordEncoder.encode("123456"), 
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
    }
}

