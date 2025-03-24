package com.rossatti.spring_pjc_2025.unidade.models;

import java.util.List;
import java.util.Set;

import com.rossatti.spring_pjc_2025.endereco.models.Endereco;
import com.rossatti.spring_pjc_2025.lotacao.models.Lotacao;

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "unidade")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(name = "unid_id")        
    private Long id;

    @Column(name = "unid_nome",length = 200,nullable = false,unique = true)
    @ToString.Include
    private String nome;

    @Column(name = "unid_sigla",length = 20,nullable = false,unique = true)
    @ToString.Include    
    private String sigla;

    @OneToMany(mappedBy = "unidade", fetch = FetchType.LAZY)
    private List<Lotacao> lotacoes;

    @ManyToMany
    @JoinTable(
        name = "unidade_endereco",
        joinColumns = @JoinColumn(name = "unid_id"),
        inverseJoinColumns = @JoinColumn(name = "end_id")
    )
    private Set<Endereco> enderecos;
}
