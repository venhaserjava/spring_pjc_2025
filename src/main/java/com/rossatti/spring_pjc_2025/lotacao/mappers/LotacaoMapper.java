package com.rossatti.spring_pjc_2025.lotacao.mappers;

import com.rossatti.spring_pjc_2025.lotacao.entities.Lotacao;
import com.rossatti.spring_pjc_2025.lotacao.dtos.response.LotacaoResponse;

public interface LotacaoMapper {

    LotacaoResponse toResponse(Lotacao lotacao);    
    
}
