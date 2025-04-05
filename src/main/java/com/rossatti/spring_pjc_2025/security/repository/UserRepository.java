package com.rossatti.spring_pjc_2025.security.repository;


import com.rossatti.spring_pjc_2025.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
