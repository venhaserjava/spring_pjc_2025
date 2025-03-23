package com.rossatti.spring_pjc_2025.endereco.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoResponse {
    private Long id;
    private String tipoLogradouro;
    private String logradouro;
    private Integer numero;
    private String bairro;
    private String cidadeNome;
}
