package com.rossatti.spring_pjc_2025.lotacao.dtos.request;

import java.time.LocalDate;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import com.rossatti.spring_pjc_2025.lotacao.validation.ValidLotacaoDate;
import com.rossatti.spring_pjc_2025.lotacao.validation.ValidPessoaExists;
import com.rossatti.spring_pjc_2025.lotacao.validation.ValidUnidadeExists;

@Data
@Builder
@ValidLotacaoDate
@NoArgsConstructor
@AllArgsConstructor
public class LotacaoRequest {

    @NotNull
    @ValidPessoaExists    
    private Long pessoaId;
    
    @NotNull
    @ValidUnidadeExists
    private Long unidadeId;

    @NotNull(message = "Data de lotação é obrigatória.")
    private LocalDate dataLotacao;

    private LocalDate dataRemocao;

    @Size(min = 1, max = 100)
    @NotBlank(message = "O campo 'portaria' é obrigatório.")   
    private String portaria;
}

/*
     @NotNull(message = "Unidade é obrigatória.")
    private Long unidadeId;

    @NotNull(message = "Data de lotação é obrigatória.")
    private LocalDate dataLotacao;

    @Size(min = 1, max = 100)
    @NotNull(message = "Portaria é obrigatória e não pode ser nula")
    @NotBlank(message = "Portaria é obrigatória e não pode estar vazia ou conter apenas espaços.")
    private String portaria;    
*/