package com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.services;

//import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.dtos.request.ServidorEfetivoRequest;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.dtos.request.ServidorEfetivoRequestDTO;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.dtos.response.ServidorEfetivoResponseDTO;

public interface ServidorEfetivoService {

    void cadastrarServidorEfetivo(ServidorEfetivoRequest dto); 

    ServidorEfetivoResponseDTO criarServidorEfetivo(ServidorEfetivoRequestDTO request);     

    Page<ServidorEfetivoResponseDTO> listarServidoresPorUnidade(Long unidadeId,Pageable pageable);
    
}
