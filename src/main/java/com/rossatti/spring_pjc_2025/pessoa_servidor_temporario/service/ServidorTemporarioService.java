package com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//import java.util.List;

import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.request.ServidorTemporarioRequest;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.response.ServidorTemporarioDTO;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.response.ServidorTemporarioResponse;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.entities.ServidorTemporario;

public interface ServidorTemporarioService {

    void create(ServidorTemporarioRequest dto);
    void update(Long id ,ServidorTemporarioRequest dto);
    Page<ServidorTemporarioDTO> findAllServidoresTemporarios(String nome, Pageable pageable);
    ServidorTemporarioResponse findByPessoaId(Long pessoaId);
    ServidorTemporarioDTO mapToDTO(ServidorTemporario servidorTemporario);
}
