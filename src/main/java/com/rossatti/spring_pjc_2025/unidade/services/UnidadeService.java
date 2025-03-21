package com.rossatti.spring_pjc_2025.unidade.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rossatti.spring_pjc_2025.unidade.dtos.request.UnidadeRequest;
import com.rossatti.spring_pjc_2025.unidade.dtos.response.UnidadeResponse;

public interface UnidadeService {

    Page<UnidadeResponse> findUnits(String unit,Pageable pageable);

    UnidadeResponse findUnitById(Long id);

    UnidadeResponse create(UnidadeRequest request);

    UnidadeResponse update(Long id,UnidadeRequest request);

}
