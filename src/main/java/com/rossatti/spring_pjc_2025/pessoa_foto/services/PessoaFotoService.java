package com.rossatti.spring_pjc_2025.pessoa_foto.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rossatti.spring_pjc_2025.pessoa_foto.dtos.request.PessoaFotoRequest;
import com.rossatti.spring_pjc_2025.pessoa_foto.dtos.response.PessoaFotoResponse;

public interface PessoaFotoService {
    PessoaFotoResponse create(PessoaFotoRequest request);
    Page<PessoaFotoResponse> findAll(Long pessoaId,Pageable pageable);
}
