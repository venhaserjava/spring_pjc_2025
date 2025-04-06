package com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServidorTemporarioResponse {    

    private Long pessoaId;
    private String pessoaNome;
    private String pessoaMae;
    private String pessoaPai;
    private String sexo; 
    private LocalDate dataNascimento;
    private String TipoServidor;
    private LocalDate dataAdmissao;
    private LocalDate dataDemissao;
    
}
