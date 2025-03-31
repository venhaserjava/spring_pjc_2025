package com.rossatti.spring_pjc_2025.lotacao.services;

import com.rossatti.spring_pjc_2025.lotacao.dtos.request.LotacaoRequest;
import com.rossatti.spring_pjc_2025.lotacao.dtos.response.LotacaoResponse;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.dtos.response.EnderecoFuncionalResponseDTO;

//import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LotacaoService {

    public LotacaoResponse create(LotacaoRequest request);

    public LotacaoResponse update(Long id, LotacaoRequest request);

    public LotacaoResponse findById(Long id);

    public Page<LotacaoResponse> findAll(Pageable pageable);

    public Page<EnderecoFuncionalResponseDTO> buscarEnderecoFuncionalPorNome(String nome,Pageable pageable);
    
}
