package com.rossatti.spring_pjc_2025.unidade.mappers;

import com.rossatti.spring_pjc_2025.unidade.dtos.request.UnidadeRequest;
import com.rossatti.spring_pjc_2025.unidade.dtos.response.UnidadeResponse;
import com.rossatti.spring_pjc_2025.unidade.entities.Unidade;

public interface UnidadeMapper {
    UnidadeResponse toResponse(Unidade model);

    Unidade toModel(UnidadeRequest request);

}
