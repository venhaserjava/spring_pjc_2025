package com.rossatti.spring_pjc_2025.servidor.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServidorEnderecoRequest {
    private String tipoLogradouro;
    private String logradouro;
    private int numero;
    private String bairro;
    private ServidorCidadeRequest cidade;
}
