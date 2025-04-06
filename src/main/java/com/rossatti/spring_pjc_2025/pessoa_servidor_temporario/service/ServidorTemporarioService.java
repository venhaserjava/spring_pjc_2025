package com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.response.ServidorTemporarioDTO;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.request.ServidorTemporarioRequest;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.response.ServidorTemporarioResponse;

public interface ServidorTemporarioService {

    void create(ServidorTemporarioRequest dto);

    void update(Long id ,ServidorTemporarioRequest dto);

    ServidorTemporarioResponse findByPessoaId(Long pessoaId);    

    Page<ServidorTemporarioDTO> findAllServidoresTemporarios(
        String nome, 
        Pageable pageable
    );
}
