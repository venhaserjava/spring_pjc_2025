package com.rossatti.spring_pjc_2025.security.repository;

import com.rossatti.spring_pjc_2025.security.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findByUsername(String username);
}
