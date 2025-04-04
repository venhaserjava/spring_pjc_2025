package com.rossatti.spring_pjc_2025.security.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
/*
 * 💡 Obs. Importante:
* Esse código é um mock (usuário fixo: admin / senha 123456).
* Quando quiser autenticar via banco, basta criar uma entidade Usuario, um UsuarioRepository, e buscar da JPA ao invés de fazer esse if.
 * 
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // ⚠️ MOCK DE USUÁRIO – aqui você pode integrar com o banco depois
        if (!username.equals("admin")) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }

        return new User(
                "admin",
                passwordEncoder.encode("123456"), // senha codificada (mesmo valor usado no login)
                Collections.emptyList() // roles/authorities se desejar
        );
    }
}
