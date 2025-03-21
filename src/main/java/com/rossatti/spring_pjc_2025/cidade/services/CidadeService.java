package com.rossatti.spring_pjc_2025.cidade.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rossatti.spring_pjc_2025.cidade.dtos.request.CidadeRequest;
import com.rossatti.spring_pjc_2025.cidade.dtos.response.CidadeResponse;
import com.rossatti.spring_pjc_2025.cidade.models.Cidade;

public interface CidadeService {
    
    public List<CidadeResponse> findAll(Pageable pageable);

    public Page<Cidade> listarCidades(String nome, Pageable pageable);

    public CidadeResponse findById(Long id);

    public CidadeResponse create(CidadeRequest request);

    public CidadeResponse update(Long id, CidadeRequest request);

}
