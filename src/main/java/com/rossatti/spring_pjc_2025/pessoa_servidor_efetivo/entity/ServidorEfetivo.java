package com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.entity;

import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;

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
@Table(name = "servidor_efetivo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServidorEfetivo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pes_id",nullable = false)
    private Long id;

    @Column(name = "se_matricula",nullable = false, length = 20)
    private String matricula;

    @OneToOne
    @JoinColumn(name = "pes_id", referencedColumnName = "pes_id", insertable = false, updatable = false)
    private Pessoa pessoa;

}
