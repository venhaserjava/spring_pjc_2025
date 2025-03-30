package com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.service;

//import java.util.List;

import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.request.ServidorTemporarioRequest;

public interface ServidorTemporarioService {

    void cadastrarServidorTemporario(ServidorTemporarioRequest dto);
    void update(Long id ,ServidorTemporarioRequest dto);
}
