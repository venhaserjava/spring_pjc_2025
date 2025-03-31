package com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.dtos.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServidorEfetivoResponseDTO {

    private Long id;
    private String nome;
    private Integer idade;
    private String matricula;
    private String unidadeNome;
    private LocalDate dataLotacao;
    private String portaria;
//    private String fotografia;
    
}
