package com.rossatti.spring_pjc_2025.unidade.services;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rossatti.spring_pjc_2025.unidade.dtos.request.UnidadeRequest;
import com.rossatti.spring_pjc_2025.unidade.dtos.response.UnidadeResponse;
import com.rossatti.spring_pjc_2025.unidade.exceptions.UnidadeNotFoundException;
import com.rossatti.spring_pjc_2025.unidade.mappers.UnidadeMapper;
import com.rossatti.spring_pjc_2025.unidade.repositories.UnidadeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnidadeServiceImpl implements UnidadeService {

    private final UnidadeRepository repository;
    private final UnidadeMapper mapper;

    @Override
    public Page<UnidadeResponse> findUnits(String unit,Pageable pageable) {
        return repository.findByNomeContaining(unit,pageable)            
            .map(mapper::toResponse);            
            
    }

    @Override
    public UnidadeResponse findUnitById(Long id) {
        return repository.findById(id)
            .map(mapper::toResponse)
            .orElseThrow(UnidadeNotFoundException::new);        
    }

    @Override
    public UnidadeResponse create(UnidadeRequest request) {
        if (request==null) {
            return null;            
        }
        var unidadeTocreate = mapper.toModel(request);
        var unidadeCreated = repository.save(unidadeTocreate);
        return mapper.toResponse(unidadeCreated);
    }
    @Override
    public UnidadeResponse update(Long id, UnidadeRequest request) {
        if (id==null || request==null) {
            throw new IllegalArgumentException("Os parametros id ou request não pode serm nulos.");            
        }
        
        var unidadeToUpdate = repository.findById(id)
                                .orElseThrow(UnidadeNotFoundException::new);
        BeanUtils.copyProperties(request,unidadeToUpdate,"id");
        var unidadeUpdated = repository.save(unidadeToUpdate);
        return mapper.toResponse(unidadeUpdated);
    }

}
