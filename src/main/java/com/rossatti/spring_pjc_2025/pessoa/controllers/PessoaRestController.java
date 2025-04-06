package com.rossatti.spring_pjc_2025.pessoa.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.rossatti.spring_pjc_2025.commons.routes.ApiRoutes;
import com.rossatti.spring_pjc_2025.pessoa.dtos.request.PessoaRequest;
import com.rossatti.spring_pjc_2025.pessoa.dtos.response.PessoaResponse;
import com.rossatti.spring_pjc_2025.pessoa.services.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PessoaRestController {

    private final PessoaService service;    

    @GetMapping(ApiRoutes.FIND_PEOPLES)
    public Page<PessoaResponse> findPeople(
        @RequestParam(name = "q",required = false,defaultValue = "") String nome,
        @PageableDefault(size = 10, sort = "nome", direction = Direction.ASC) Pageable pageable
    ){
        return service.findPeople(nome, pageable);
    }

    @GetMapping(ApiRoutes.FIND_PERSON_BY_ID)
    public PessoaResponse findPersonById(@PathVariable Long id){
        return service.findPersonById(id);
    }
    
    @PostMapping(ApiRoutes.CREATE_PEOPLE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public PessoaResponse create(@RequestBody @Valid PessoaRequest request){
        return service.create(request);
    }

    @PutMapping(ApiRoutes.UPDATE_PEOPLE)
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public PessoaResponse update(@PathVariable Long id, @RequestBody @Valid PessoaRequest request){
        return service.update(id, request);
    }
}
