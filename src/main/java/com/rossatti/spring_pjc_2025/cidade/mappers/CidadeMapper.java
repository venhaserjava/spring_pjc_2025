package com.rossatti.spring_pjc_2025.cidade.mappers;

import com.rossatti.spring_pjc_2025.cidade.dtos.request.CidadeRequest;
import com.rossatti.spring_pjc_2025.cidade.dtos.response.CidadeResponse;
import com.rossatti.spring_pjc_2025.cidade.entities.Cidade;

public interface CidadeMapper {

    CidadeResponse toResponse(Cidade model);
    Cidade toModel(CidadeRequest  request);

}
