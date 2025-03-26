package com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.models;

import jakarta.persistence.Entity;
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


    private Long id;

    private String matricula;
}
