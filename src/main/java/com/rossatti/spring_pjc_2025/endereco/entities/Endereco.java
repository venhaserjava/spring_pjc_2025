package com.rossatti.spring_pjc_2025.endereco.entities;

//import java.util.HashSet;
import java.util.Set;

import com.rossatti.spring_pjc_2025.cidade.entities.Cidade;
//import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
//import com.rossatti.spring_pjc_2025.unidade.entities.Unidade;
import com.rossatti.spring_pjc_2025.unidade.entities.UnidadeEndereco;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "endereco", 
    uniqueConstraints = @UniqueConstraint(columnNames = {"end_logradouro", "end_bairro", "end_numero", "cid_id"})
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "end_id") 
    private Long id;

    @Column(name = "end_tipo_logradouro", nullable = false, length = 50)
    @EqualsAndHashCode.Include
    private String tipoLogradouro;

    @Column(name = "end_logradouro", nullable = false, length = 200)
    @EqualsAndHashCode.Include
    private String logradouro;

    @Column(name = "end_numero", nullable = false)
    @EqualsAndHashCode.Include
    private int numero;

    @Column(name = "end_bairro", nullable = false, length = 100)
    @EqualsAndHashCode.Include
    private String bairro;

    @ManyToOne
    @JoinColumn(name = "cid_id", nullable = false)
    @EqualsAndHashCode.Include
    private Cidade cidade; 

    @OneToMany(mappedBy = "endereco")
    private Set<UnidadeEndereco> unidadeEnderecos;
}


/*
@Entity
@Table(
    name = "endereco", 
    uniqueConstraints =     @UniqueConstraint( columnNames = {"end_logradouro", "end_bairro","end_numero","cid_id"}   )
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "end_id")    
    private Long id;
    
    @Column(name = "end_tipo_logradouro",nullable = false, length = 50)
    @EqualsAndHashCode.Include
    private String tipoLogradouro;

    @Column(name = "end_logradouro",nullable = false,length = 200)
    @EqualsAndHashCode.Include
    private String logradouro;

    @Column(name = "end_numero",nullable = false)
    @EqualsAndHashCode.Include
    private int numero;
    
    @Column(name = "end_bairro",nullable = false,length = 100)
    @EqualsAndHashCode.Include
    private String bairro;

    @ManyToOne
    @JoinColumn(name = "cid_id", nullable = false)
    @EqualsAndHashCode.Include
    private Cidade cidade;    
    
    @ManyToMany(mappedBy = "enderecos")
    private Set<Unidade> unidades = new HashSet<>();

    @ManyToMany(mappedBy = "enderecos")
    private Set<Pessoa> pessoas = new HashSet<>();

}

*/