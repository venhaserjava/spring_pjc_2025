package com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.response;

import java.time.LocalDate;

//import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
//@AllArgsConstructor
public class ServidorTemporarioDTO {

    private Long id;
    private String nome;
    private String mae;
    private String pai;
    private String sexo;
    private LocalDate dataNascimento;
    private String tipoLogradouro;
    private String logradouro;
    private Integer numero;
    private String bairro;
    private String cidadeNome;
    private String tipoServidor;
    private LocalDate dataAdmissao;
    private LocalDate dataDemissao;

    public ServidorTemporarioDTO(Long id, String nome, String mae, String pai, String sexo,
                                 LocalDate dataNascimento, String tipoLogradouro, String logradouro, Integer numero,
                                 String bairro, String cidadeNome,String tipoServidor ,LocalDate dataAdmissao, LocalDate dataDemissao) {
        this.id = id;
        this.nome = nome;
        this.mae = mae;
        this.pai = pai;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.tipoLogradouro = tipoLogradouro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidadeNome = cidadeNome;
        this.tipoServidor = tipoServidor;
        this.dataAdmissao = dataAdmissao;
        this.dataDemissao = dataDemissao;
    }

    // Getters and setters (ou você pode usar @Data do Lombok)
}
