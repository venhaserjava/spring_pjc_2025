package com.rossatti.spring_pjc_2025.lotacao.services;


import com.rossatti.spring_pjc_2025.lotacao.dtos.request.LotacaoRequest;
import com.rossatti.spring_pjc_2025.lotacao.dtos.response.LotacaoResponse;

import java.util.List;

public interface LotacaoService {

    LotacaoResponse criarLotacao(LotacaoRequest request);

    LotacaoResponse atualizarLotacao(Long id, LotacaoRequest request);

    LotacaoResponse buscarPorId(Long id);

    List<LotacaoResponse> listarTodas();

    void deletarLotacao(Long id);
}
