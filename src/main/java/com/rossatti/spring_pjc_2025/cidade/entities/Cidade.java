package com.rossatti.spring_pjc_2025.cidade.entities;

import java.util.List;
import jakarta.persistence.CascadeType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rossatti.spring_pjc_2025.endereco.entities.Endereco;

import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.UniqueConstraint;

import lombok.Data;
import lombok.Builder;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "cidade", 
    uniqueConstraints =     @UniqueConstraint( columnNames = {"cid_nome", "cid_uf"}   )
)
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cidade {

    @Id
    @Column(name = "cid_id")
    @ToString.Include
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Include
    @Column(name = "cid_nome",length = 200,nullable = false)        
    private String nome;

    @Column(name = "cid_uf",length = 2,nullable = false)  
    private String uf;

    @PrePersist
    @PreUpdate
    private void formatarUf() {
        this.uf = this.uf != null ? this.uf.toUpperCase() : null;
    }
    
    @OneToMany(mappedBy = "cidade",cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    @JsonIgnore
    private List<Endereco> enderecos;

}
