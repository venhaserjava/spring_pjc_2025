package com.rossatti.spring_pjc_2025.pessoa.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.rossatti.spring_pjc_2025.endereco.entities.Endereco;
import com.rossatti.spring_pjc_2025.lotacao.entities.Lotacao;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name = "pes_id")
    private Long id;

    @Column(name = "pes_nome",length = 200,nullable = false,unique = true)
    private String nome;

    @Column(name = "pes_mae", length = 200, nullable = false)
    private String mae;

    @Column(name = "pes_pai", length = 200)
    private String pai;
    
    @Column(name = "pes_sexo", length = 10, nullable = false)
    private String sexo; 

    @Column(name = "pes_data_nascimento",nullable = false)    
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "pessoa",cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Lotacao> lotacoes;

    @ManyToMany
    @JoinTable(
        name = "pessoa_endereco",
        joinColumns = @JoinColumn(name = "pes_id"),
        inverseJoinColumns = @JoinColumn(name = "end_id")
    )
    private Set<Endereco> enderecos = new HashSet<>();


}
