package com.rossatti.spring_pjc_2025.pessoa_foto.dtos.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaFotoResponse {
    private Long id;
    private String pessoaNome;
    private LocalDate data;
    private String bucket;
    private String hash;
}
