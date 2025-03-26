package com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.services;

import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.dtos.request.ServidorEfetivoRequest;

public interface ServidorEfetivoService {
    void cadastrarServidorEfetivo(ServidorEfetivoRequest dto);
}
