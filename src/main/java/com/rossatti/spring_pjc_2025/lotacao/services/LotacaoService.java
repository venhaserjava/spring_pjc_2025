package com.rossatti.spring_pjc_2025.lotacao.services;


import com.rossatti.spring_pjc_2025.lotacao.dtos.request.LotacaoRequest;
import com.rossatti.spring_pjc_2025.lotacao.dtos.response.LotacaoResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LotacaoService {

    LotacaoResponse create(LotacaoRequest request);

    LotacaoResponse update(Long id, LotacaoRequest request);

    LotacaoResponse findPostingById(Long id);

    Page<LotacaoResponse> findAllPosting(Pageable pageable);

    void delete(Long id);
}
