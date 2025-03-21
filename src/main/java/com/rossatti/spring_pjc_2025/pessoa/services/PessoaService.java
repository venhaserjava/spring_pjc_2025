package com.rossatti.spring_pjc_2025.pessoa.services;

//import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rossatti.spring_pjc_2025.pessoa.dtos.request.PessoaRequest;
import com.rossatti.spring_pjc_2025.pessoa.dtos.response.PessoaResponse;

public interface PessoaService {

//    public List<PessoaResponse> findAll();

    public Page<PessoaResponse> findPeople(String nome,Pageable pageable);

    public PessoaResponse findPersonById(Long id);

    public PessoaResponse create(PessoaRequest request);

    public PessoaResponse update(Long id, PessoaRequest request);


}
