package com.rossatti.spring_pjc_2025.lotacao.dtos.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LotacaoResponse {
    
    private Long id;
    private Long pessoaId;
    private String pessoaNome; // Nome da pessoa associada à lotação
    private Long unidadeId;
    private String unidadeNome; // Nome da unidade associada
    private LocalDate dataLotacao;
    private LocalDate dataRemocao;
    private String portaria;
}
