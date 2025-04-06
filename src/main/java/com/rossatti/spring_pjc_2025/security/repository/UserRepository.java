package com.rossatti.spring_pjc_2025.security.repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.rossatti.spring_pjc_2025.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

     Optional<User> findByUsername(String username);

}
