package com.rossatti.spring_pjc_2025.endereco.services;

import java.util.List;

import com.rossatti.spring_pjc_2025.endereco.dtos.request.EnderecoRequest;
import com.rossatti.spring_pjc_2025.endereco.dtos.response.EnderecoResponse;

public interface EnderecoService {
    EnderecoResponse create(EnderecoRequest dto);
    EnderecoResponse findById(Long id);
    List<EnderecoResponse> findAll();
    EnderecoResponse update(Long id, EnderecoRequest dto);
    void delete(Long id);
}
