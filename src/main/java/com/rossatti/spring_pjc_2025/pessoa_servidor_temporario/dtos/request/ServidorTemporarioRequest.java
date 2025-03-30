package com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.request;

//import com.rossatti.spring_pjc_2025.lotacao.dtos.LotacaoDTO;
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
public class ServidorTemporarioRequest {

    @NotNull(message = "O ID da pessoa é obrigatório.")
    private Long pessoaId;

//    @NotNull(message = "A data de admissão é obrigatória.")
    private LocalDate dataAdmissao;

    private LocalDate dataDemissao;

//    @NotNull(message = "Os dados de lotação são obrigatórios.")
//    private LotacaoDTO lotacao;

}
