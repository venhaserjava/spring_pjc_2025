package com.rossatti.spring_pjc_2025.cidade.models;

import java.util.List;

import com.rossatti.spring_pjc_2025.endereco.models.Endereco;
import jakarta.persistence.CascadeType;

//import com.rossatti.spring_pjc_2025.cidade.validators.ValidUF;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "cidade", 
    uniqueConstraints =     @UniqueConstraint( columnNames = {"cid_nome", "cid_uf"}   )
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Cidade {

    @Id
    @Column(name = "cid_id")
    @EqualsAndHashCode.Include
    @ToString.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Include
    @Column(name = "cid_nome",length = 200,nullable = false)        
    private String nome;

//    @ValidUF
    @Column(name = "cid_uf",length = 2,nullable = false)  
    private String uf;

    @PrePersist
    @PreUpdate
    private void formatarUf() {
        this.uf = this.uf != null ? this.uf.toUpperCase() : null;
    }
    @OneToMany(mappedBy = "endereco",cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Endereco> enderecos;

}
