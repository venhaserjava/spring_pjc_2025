package com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.mappers;

import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.response.ServidorTemporarioResponse;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.entities.ServidorTemporario;

public interface ServidorTemporarioMapper {
    ServidorTemporarioResponse toResponse(Pessoa model,ServidorTemporario servidor);
}
