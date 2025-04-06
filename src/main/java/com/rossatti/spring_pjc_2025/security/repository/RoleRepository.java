package com.rossatti.spring_pjc_2025.security.repository;

import java.util.Optional;

import com.rossatti.spring_pjc_2025.security.model.Role;
import com.rossatti.spring_pjc_2025.security.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);

}
