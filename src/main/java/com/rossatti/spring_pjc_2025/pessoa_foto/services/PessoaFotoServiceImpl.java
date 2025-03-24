package com.rossatti.spring_pjc_2025.pessoa_foto.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rossatti.spring_pjc_2025.pessoa.models.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa.repositories.PessoaRepository;
import com.rossatti.spring_pjc_2025.pessoa_foto.dtos.request.PessoaFotoRequest;
import com.rossatti.spring_pjc_2025.pessoa_foto.dtos.response.PessoaFotoResponse;
import com.rossatti.spring_pjc_2025.pessoa_foto.exceptions.PessoaFotoNotFoundException;
import com.rossatti.spring_pjc_2025.pessoa_foto.mappers.PessoaFotoMapper;
import com.rossatti.spring_pjc_2025.pessoa_foto.models.PessoaFoto;
import com.rossatti.spring_pjc_2025.pessoa_foto.repository.PessoaFotoRepository;

@Service
public class PessoaFotoServiceImpl implements PessoaFotoService {


    @Autowired
    private PessoaFotoRepository repository;
    
    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Autowired
    private PessoaFotoMapper mapper;

    @Override
       public PessoaFotoResponse create(PessoaFotoRequest request) {
        Pessoa pessoa = pessoaRepository.findById(request.getPessoaId())
                .orElseThrow(() -> new PessoaFotoNotFoundException("Pessoa não encontrada"));
        PessoaFoto PessoaFoto = repository.save(mapper.toModel(request, pessoa));
        return mapper.toResponse(PessoaFoto);    }

    @Override
    public Page<PessoaFotoResponse> findAll(Long pessoaId, Pageable pageable) {
        return repository.findByPessoaIdOrderByDataDesc(pessoaId,pageable)
                         .map(mapper::toResponse);            
    }

}
