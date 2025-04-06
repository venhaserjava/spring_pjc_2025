package com.rossatti.spring_pjc_2025.pessoa.services;


import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rossatti.spring_pjc_2025.pessoa.mappers.PessoaMapper;
import com.rossatti.spring_pjc_2025.pessoa.dtos.request.PessoaRequest;
import com.rossatti.spring_pjc_2025.pessoa.dtos.response.PessoaResponse;
import com.rossatti.spring_pjc_2025.pessoa.repositories.PessoaRepository;
import com.rossatti.spring_pjc_2025.pessoa.exceptions.PessoaNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository repository;
    private final PessoaMapper mapper;

    @Override
    public Page<PessoaResponse> findPeople(
        String nome,
        Pageable pageable
    ){
        return repository.findByNomeContaining(nome, pageable)
                .map(mapper::toResponse);
    }    

    @Override
    public PessoaResponse findPersonById(Long id) {

        return repository.findById(id)
        .map(mapper::toResponse)
        .orElseThrow(PessoaNotFoundException::new);        
    }        

    @Override
    public PessoaResponse create(PessoaRequest request){

        if (request==null) {
            throw new IllegalArgumentException("Os parametro request não pode serm nulo.");  
        }
        var pessoaToCreate = mapper.toModel(request);
        var pessoaCreated = repository.save(pessoaToCreate);

        return mapper.toResponse(pessoaCreated);

    }

    @Override
    public PessoaResponse update(
        Long id, 
        PessoaRequest request
    ){

        if (id==null || request==null) {
            throw new IllegalArgumentException("Os parametros id ou request não pode serm nulos.");  
        }        

        var pessoaToUpdate = repository.findById(id)
                .orElseThrow(PessoaNotFoundException::new);
        BeanUtils.copyProperties(request,pessoaToUpdate ,"id");        
        var pessoaUpdated = repository.save(pessoaToUpdate);

        return mapper.toResponse(pessoaUpdated);        
    }
}
