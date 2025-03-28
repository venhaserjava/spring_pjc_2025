package com.rossatti.spring_pjc_2025.lotacao.mappers;

import org.springframework.stereotype.Component;

import com.rossatti.spring_pjc_2025.lotacao.dtos.response.LotacaoResponse;
import com.rossatti.spring_pjc_2025.lotacao.entities.Lotacao;

@Component
public class LotacaoMapperImpl implements LotacaoMapper {

    @Override
    public LotacaoResponse toResponse(Lotacao lotacao) {
        return LotacaoResponse.builder()
                .id(lotacao.getId())
                .pessoaId(lotacao.getPessoa().getId())
                .pessoaNome(lotacao.getPessoa().getNome())
                .unidadeId(lotacao.getUnidade().getId())
                .unidadeNome(lotacao.getUnidade().getNome())
                .dataLotacao(lotacao.getDataLotacao())
                .dataRemocao(lotacao.getDataRemocao())
                .portaria(lotacao.getPortaria())
                .build();
    }

}
