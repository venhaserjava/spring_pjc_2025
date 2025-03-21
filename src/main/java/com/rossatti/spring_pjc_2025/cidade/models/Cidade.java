package com.rossatti.spring_pjc_2025.cidade.models;

import com.rossatti.spring_pjc_2025.cidade.validators.ValidUF;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(
    name = "cidade", 
    uniqueConstraints =     @UniqueConstraint( columnNames = {"cid_nome", "cid_uf"}   )
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid_id")
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @ToString.Include
    @Column(name = "cid_nome",length = 200,nullable = false)        
    private String nome;

    @ValidUF
    @Column(name = "cid_uf",length = 2,nullable = false)  
    private String uf;
}
