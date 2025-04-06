package com.rossatti.spring_pjc_2025.endereco.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rossatti.spring_pjc_2025.cidade.entities.Cidade;
import com.rossatti.spring_pjc_2025.cidade.repositories.CidadeRepository;
import com.rossatti.spring_pjc_2025.cidade.exceptions.CidadeNotFoundException;

import com.rossatti.spring_pjc_2025.endereco.dtos.request.EnderecoRequest;
import com.rossatti.spring_pjc_2025.endereco.dtos.response.EnderecoResponse;

import com.rossatti.spring_pjc_2025.endereco.entities.Endereco;
import com.rossatti.spring_pjc_2025.endereco.mappers.EnderecoMapper;
import com.rossatti.spring_pjc_2025.endereco.repositories.EnderecoRepository;
import com.rossatti.spring_pjc_2025.endereco.exceptions.EnderecoNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoRepository repository;
    private final CidadeRepository cidadeRepository;
    private final EnderecoMapper mapper;

    public EnderecoServiceImpl(
        EnderecoRepository enderecoRepository, 
        CidadeRepository cidadeRepository, 
        EnderecoMapper enderecoMapper
    ) {
        this.repository = enderecoRepository;
        this.cidadeRepository = cidadeRepository;
        this.mapper = enderecoMapper;
    }

    @Override
    public Page<EnderecoResponse> findAll(
        String logradouro,
        Pageable pageable
    ) {
        return repository.findByLogradouroContaining(logradouro, pageable)
                           .map(mapper::toResponse) ;
    }
    
    @Override
    public EnderecoResponse findById(Long id) {

        if(!repository.existsById(id)){
            return new EnderecoResponse();
        }
        
        Endereco endereco = repository.findById(id)
        .orElseThrow(() -> new EnderecoNotFoundException("Endereço não encontrado"));

        return mapper.toResponse(endereco);
    }    

    @Override
    @Transactional
    public EnderecoResponse create(EnderecoRequest dto) {
        
        Cidade cidade = cidadeRepository.findById(dto.getCidadeId())
                .orElseThrow(() -> new CidadeNotFoundException("Cidade não encontrada"));

        Endereco endereco = mapper.toModel(dto, cidade);
        endereco = repository.save(endereco);

        return mapper.toResponse(endereco);
    }    

    @Override
    @Transactional
    public EnderecoResponse update(Long id, EnderecoRequest dto) {

        Endereco endereco = repository.findById(id)
                .orElseThrow(() -> new EnderecoNotFoundException("Endereço não encontrado"));

        Cidade cidade = cidadeRepository.findById(dto.getCidadeId())
                .orElseThrow(() -> new CidadeNotFoundException("Cidade não encontrada"));

        endereco = mapper.toModel(dto, cidade);
        endereco.setId(id);
        endereco = repository.save(endereco);

        return mapper.toResponse(endereco);
    }
}