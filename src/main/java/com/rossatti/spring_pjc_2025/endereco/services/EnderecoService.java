package com.rossatti.spring_pjc_2025.endereco.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rossatti.spring_pjc_2025.endereco.dtos.request.EnderecoRequest;
import com.rossatti.spring_pjc_2025.endereco.dtos.response.EnderecoResponse;

public interface EnderecoService {
    EnderecoResponse create(EnderecoRequest dto);
    EnderecoResponse findById(Long id);
    Page<EnderecoResponse> findAll(Pageable pageable);
    EnderecoResponse update(Long id, EnderecoRequest dto);
    void delete(Long id);
}
