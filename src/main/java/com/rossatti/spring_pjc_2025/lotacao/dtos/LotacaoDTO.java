package com.rossatti.spring_pjc_2025.lotacao.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LotacaoDTO {
    
    @NotNull(message = "Unidade é obrigatória.")
    private Long unidadeId;

    @NotNull(message = "Data de lotação é obrigatória.")
    private LocalDate dataLotacao;

    @NotNull(message = "Portaria é obrigatória e não pode estar vazia ou conter apenas espaços.")
    private String portaria;
    
}