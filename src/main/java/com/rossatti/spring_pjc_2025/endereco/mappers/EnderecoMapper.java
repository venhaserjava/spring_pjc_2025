package com.rossatti.spring_pjc_2025.endereco.mappers;

import com.rossatti.spring_pjc_2025.cidade.models.Cidade;
import com.rossatti.spring_pjc_2025.endereco.dtos.request.EnderecoRequest;
import com.rossatti.spring_pjc_2025.endereco.dtos.response.EnderecoResponse;
import com.rossatti.spring_pjc_2025.endereco.models.Endereco;

public interface EnderecoMapper {
    Endereco toModel(EnderecoRequest dto, Cidade cidade);
    EnderecoResponse toResponse(Endereco endereco);
}
