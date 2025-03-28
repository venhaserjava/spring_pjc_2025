package com.rossatti.spring_pjc_2025.lotacao.entities;

import java.time.LocalDate;
//import java.util.Date;

import com.rossatti.spring_pjc_2025.pessoa.models.Pessoa;
import com.rossatti.spring_pjc_2025.unidade.models.Unidade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
// import jakarta.persistence.Temporal;
// import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lotacao")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lotacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lot_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pes_id",nullable = false)
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "unid_id",nullable = false)
    private Unidade unidade;

    @Column(name = "lot_data_lotacao",nullable = false)
    private LocalDate dataLotacao;

    @Column(name = "lot_data_remocao",nullable = true)    
    private LocalDate dataRemocao;

    @Column(name = "lot_portaria",length = 100)
    private String portaria;
}
