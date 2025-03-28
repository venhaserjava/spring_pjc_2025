package com.rossatti.spring_pjc_2025.servidor.mappers;

import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.servidor.dtos.request.ServidorRequest;
import com.rossatti.spring_pjc_2025.servidor.dtos.response.ServidorResponse;

public interface ServidorMapper {

    ServidorResponse toResponse(Pessoa model);
    Pessoa toModel(ServidorRequest request);
}
