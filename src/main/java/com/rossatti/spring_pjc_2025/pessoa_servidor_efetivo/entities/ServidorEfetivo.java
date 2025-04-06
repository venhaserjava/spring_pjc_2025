package com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.entities;

import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Table(name = "servidor_efetivo")
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ServidorEfetivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "se_id", nullable = false)
    private Long id;

    @Column(name = "se_matricula", nullable = false, length = 20)
    private String matricula;

    @OneToOne
    @JoinColumn(name = "pes_id", referencedColumnName = "pes_id", nullable = false)
    private Pessoa pessoa;
}

