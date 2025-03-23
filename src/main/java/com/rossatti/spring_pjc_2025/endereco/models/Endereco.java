package com.rossatti.spring_pjc_2025.endereco.models;

import com.rossatti.spring_pjc_2025.cidade.models.Cidade;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "endereco", 
    uniqueConstraints =     @UniqueConstraint( columnNames = {"end_logradouro", "end_bairro","end_numero","cid_id"}   )
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    @Column(name = "end_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "end_tipo_logradouro",nullable = false, length = 50)
    private String tipoLogradouro;

    @Column(name = "end_logradouro",nullable = false,length = 200)
    private String logradouro;

    @Column(name = "end_numero",nullable = false)
    private int numero;
    
    @Column(name = "end_bairro",nullable = false,length = 100)
    private String bairro;

    @ManyToOne
    @Column(name = "cid_id",nullable = false)
    private Cidade cidade;
    
    
}
