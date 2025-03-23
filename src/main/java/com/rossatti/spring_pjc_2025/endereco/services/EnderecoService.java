package com.rossatti.spring_pjc_2025.endereco.services;

import java.util.List;

import com.rossatti.spring_pjc_2025.endereco.dtos.request.EnderecoRequest;
import com.rossatti.spring_pjc_2025.endereco.dtos.response.EnderecoResponse;

public interface EnderecoService {
    EnderecoResponse criarEndereco(EnderecoRequest dto);
    EnderecoResponse buscarPorId(Long id);
    List<EnderecoResponse> listarTodos();
    EnderecoResponse atualizarEndereco(Long id, EnderecoRequest dto);
    void deletarEndereco(Long id);
}
