package com.rossatti.spring_pjc_2025.servidor.dtos.response;

import java.time.LocalDate;
// import java.util.Set;
// import com.rossatti.spring_pjc_2025.endereco.dtos.response.EnderecoResponse;
// import com.rossatti.spring_pjc_2025.endereco.entities.Endereco;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServidorResponse {
    private Long id;
    private String nome;
    private String mae;
    private String pai;
    private String sexo;
    private LocalDate dataNascimento;
    private String fotoUrl;
    private String tipoLogradouro;
    private String logradouro;
    private Integer numero;
    private String bairro;
    private String cidadeNome;
    private String foto;
}

