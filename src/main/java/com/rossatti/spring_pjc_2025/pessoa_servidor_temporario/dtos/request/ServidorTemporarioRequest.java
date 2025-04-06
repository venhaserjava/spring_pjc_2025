package com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.request;

import java.time.LocalDate;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServidorTemporarioRequest {

    @NotNull(message = "O ID da pessoa é obrigatório.")
    private Long pessoaId;

    private LocalDate dataAdmissao;

    private LocalDate dataDemissao;
}
