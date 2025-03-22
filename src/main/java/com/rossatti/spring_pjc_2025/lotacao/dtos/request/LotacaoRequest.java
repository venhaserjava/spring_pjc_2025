package com.rossatti.spring_pjc_2025.lotacao.dtos.request;

import java.time.LocalDate;
//import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.rossatti.spring_pjc_2025.lotacao.validation.ValidLotacaoDate;
import com.rossatti.spring_pjc_2025.lotacao.validation.ValidPessoaExists;
import com.rossatti.spring_pjc_2025.lotacao.validation.ValidUnidadeExists;
import com.rossatti.spring_pjc_2025.pessoa.models.Pessoa;
import com.rossatti.spring_pjc_2025.unidade.models.Unidade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ValidLotacaoDate
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LotacaoRequest {

    @NotNull
    @ValidPessoaExists    
    private Pessoa pessoa;
    @NotNull
    @ValidUnidadeExists
    private Unidade unidade;

    @NotNull
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate dataLotacao;
    
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate dataRemocao;

    @NotNull
    @NotEmpty
    @Size(min = 1,max = 100)
    @NotBlank(message = "os Dados da Portaria são obrigatórios")   
    private String portaria;
}
