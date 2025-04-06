package com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.dtos.request;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServidorEfetivoRequestDTO {

    @NotNull(message = "O ID da pessoa é obrigatório")
    private Long pessoaId;

    @NotBlank(message = "A matrícula é obrigatória")
    private String matricula;

    @NotNull(message = "O ID da unidade é obrigatório")
    private Long unidadeId;

    @NotNull(message = "A data de lotação é obrigatória")
    private LocalDate dataLotacao;

    private String portaria;
    
}