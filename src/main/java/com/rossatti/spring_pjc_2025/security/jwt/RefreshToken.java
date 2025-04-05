package com.rossatti.spring_pjc_2025.security.jwt;


import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

import com.rossatti.spring_pjc_2025.security.model.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
