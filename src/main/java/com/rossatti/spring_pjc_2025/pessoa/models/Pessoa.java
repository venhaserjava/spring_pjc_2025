package com.rossatti.spring_pjc_2025.pessoa.models;

import java.time.LocalDate;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String name;

    @Column(name = "pes_mae", length = 200, nullable = false)
    private String mae;

    @Column(name = "pes_pai", length = 200)
    private String pai;
    
    @Column(name = "pes_sexo", length = 10, nullable = false)
    private String sexo; 

    @Column(name = "pes_data_nascimento")
    @JdbcTypeCode(SqlTypes.DATE)
    private LocalDate dateOfBirth;

}
