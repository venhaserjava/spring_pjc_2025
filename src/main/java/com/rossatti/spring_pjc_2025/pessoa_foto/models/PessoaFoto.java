package com.rossatti.spring_pjc_2025.pessoa_foto.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;

@Entity
@Table(name = "foto_pessoa")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PessoaFoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fp_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pes_id", nullable = false)
    private Pessoa pessoa;

    @Column(name = "fp_data", nullable = false)
    private LocalDate data;

    @Column(name = "fp_bucket", length = 50, nullable = false)
    private String bucket;

    @Column(name = "fp_hash", length = 50, nullable = false, unique = true)
    private String hash;
}