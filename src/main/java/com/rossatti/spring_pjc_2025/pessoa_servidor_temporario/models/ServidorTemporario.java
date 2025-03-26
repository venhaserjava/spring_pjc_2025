package com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.models;

import java.time.LocalDate;

import com.rossatti.spring_pjc_2025.pessoa.models.Pessoa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "servidor_temporario")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServidorTemporario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "st_id",nullable = false)
    private Long id;
    
    @Column(name = "pes_id", nullable = false)
    private Long pessoaId;

    @Column(name = "st_data_admissao", nullable = false)
    private LocalDate dataAdmissao;

    @Column(name = "st_data_demissao")
    private LocalDate dataDemissao;

    @OneToOne
    @JoinColumn(name = "pes_id", referencedColumnName = "pes_id", insertable = false, updatable = false)
    private Pessoa pessoa;

}
