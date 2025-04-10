package com.rossatti.spring_pjc_2025.cidade.services;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rossatti.spring_pjc_2025.cidade.dtos.request.CidadeRequest;
import com.rossatti.spring_pjc_2025.cidade.dtos.response.CidadeResponse;

import com.rossatti.spring_pjc_2025.cidade.entities.Cidade;
import com.rossatti.spring_pjc_2025.cidade.mappers.CidadeMapper;
import com.rossatti.spring_pjc_2025.cidade.repositories.CidadeRepository;
import com.rossatti.spring_pjc_2025.cidade.exceptions.CidadeNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CidadeServiceImpl implements CidadeService {

    private final CidadeMapper mapper;
    private final CidadeRepository repository;

    @Override
    public Page<CidadeResponse> findAll(
        String nome, 
        Pageable pageable
    ) {
        return repository.findByNomeContaining(nome, pageable)
                            .map(mapper::toResponse);
    }       

    @Override
    public CidadeResponse findById(Long id) {

        if(! repository.existsById(id)){
            return new CidadeResponse();                    
        }

        return repository.findById(id)
            .map(mapper::toResponse)
            .orElseThrow(CidadeNotFoundException::new);
    }

    @Override
    public CidadeResponse create(CidadeRequest request) {
        if (request==null) {
            return null;            
        }
                           
        var cidadeToCreate = mapper.toModel(request);
        var cidadeCreated = repository.save(cidadeToCreate);
        return mapper.toResponse(cidadeCreated);        
    }

    @Override
    public CidadeResponse update(Long id, CidadeRequest request) {
        if (id==null || request==null) {
            throw new IllegalArgumentException("Os Parâmetros id e request não pode ser nulo");            
        }        
        var cidadeToUpdate = repository.findById(id)
                            .orElseThrow(CidadeNotFoundException::new);                            
        
        BeanUtils.copyProperties(request,cidadeToUpdate,"id");
        var cidadeUpdated = repository.save(cidadeToUpdate);
        return mapper.toResponse(cidadeUpdated);                
    }
    @Override
    public Cidade criarCidadeSeNaoExistir(String nome, String uf) {
        return repository.findByNomeAndUf(nome, uf)
            .orElseGet(() -> repository.save(new Cidade(null, nome, uf, null)));
    }

}
