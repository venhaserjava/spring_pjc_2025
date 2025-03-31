package com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoFuncionalResponseDTO {
    private String nomeServidor;
    private String nomeUnidade;
    private String logradouro;
    private Integer numero;
    private String bairro;
    private String cidade;
    private String uf;
}
