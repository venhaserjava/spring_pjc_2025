package com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.dtos.request;

//import com.rossatti.spring_pjc_2025.lotacao.dtos.LotacaoDTO;
import com.rossatti.spring_pjc_2025.lotacao.dtos.request.LotacaoRequest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServidorEfetivoRequest {

    @NotNull(message = "O ID da pessoa é obrigatório.")
    private Long pessoaId;

    @NotNull(message = "matricula é obrigatória e não pode estar vazia ou conter apenas espaços.")
    private String matricula;

    @NotNull(message = "Os dados de lotação são obrigatórios.")
    private LotacaoRequest lotacao;    
//    private LotacaoDTO lotacao;    

}
