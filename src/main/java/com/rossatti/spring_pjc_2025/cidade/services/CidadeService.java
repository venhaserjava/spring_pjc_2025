package com.rossatti.spring_pjc_2025.cidade.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rossatti.spring_pjc_2025.cidade.entities.Cidade;
import com.rossatti.spring_pjc_2025.cidade.dtos.request.CidadeRequest;
import com.rossatti.spring_pjc_2025.cidade.dtos.response.CidadeResponse;

public interface CidadeService {
    
    public CidadeResponse findById(Long id);
    
    public CidadeResponse create(CidadeRequest request);
    
    public CidadeResponse update(Long id, CidadeRequest request);
    
    public Cidade criarCidadeSeNaoExistir(String nome, String uf);
    
    public Page<CidadeResponse> findAll(String nome, Pageable pageable);
}
