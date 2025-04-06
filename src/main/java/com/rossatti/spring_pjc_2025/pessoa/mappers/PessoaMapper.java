package com.rossatti.spring_pjc_2025.pessoa.mappers;

import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa.dtos.request.PessoaRequest;
import com.rossatti.spring_pjc_2025.pessoa.dtos.response.PessoaResponse;

public interface PessoaMapper {
    
    PessoaResponse toResponse(Pessoa model);
    Pessoa toModel(PessoaRequest request);
}
