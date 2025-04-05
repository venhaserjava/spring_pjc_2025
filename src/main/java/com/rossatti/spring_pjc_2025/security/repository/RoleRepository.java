package com.rossatti.spring_pjc_2025.security.repository;

import com.rossatti.spring_pjc_2025.security.enums.RoleName;
import com.rossatti.spring_pjc_2025.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
