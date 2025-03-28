package com.rossatti.spring_pjc_2025.lotacao.mappers;

import com.rossatti.spring_pjc_2025.lotacao.dtos.response.LotacaoResponse;
import com.rossatti.spring_pjc_2025.lotacao.entities.Lotacao;

public interface LotacaoMapper {
    LotacaoResponse toResponse(Lotacao lotacao);    
}
