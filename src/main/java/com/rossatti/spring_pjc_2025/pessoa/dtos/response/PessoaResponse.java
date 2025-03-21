package com.rossatti.spring_pjc_2025.pessoa.dtos.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PessoaResponse {
    private Long id;
    private String nome;
    private String mae;
    private String pai;
    private String sexo; 
    private LocalDate dataNascimento;
}
