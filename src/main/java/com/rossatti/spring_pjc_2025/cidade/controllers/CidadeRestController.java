package com.rossatti.spring_pjc_2025.cidade.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rossatti.spring_pjc_2025.cidade.dtos.request.CidadeRequest;
import com.rossatti.spring_pjc_2025.cidade.dtos.response.CidadeResponse;
import com.rossatti.spring_pjc_2025.cidade.services.CidadeService;
import com.rossatti.spring_pjc_2025.commons.routes.ApiRoutes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CidadeRestController {

    private final CidadeService service;

    @GetMapping(ApiRoutes.FIND_CITIES)  
    public ResponseEntity<Page<CidadeResponse>> findAll(
        @RequestParam(required = false, defaultValue = "") String nome,
        @PageableDefault(size = 10, sort = "nome", direction = Direction.ASC) Pageable pageable) {

        Page<CidadeResponse> cidades = service.findAll(nome, pageable);
        return ResponseEntity.ok(cidades);
    }     

    @GetMapping(ApiRoutes.FIND_CITY_BY_ID)
    public CidadeResponse findById(@PathVariable Long id){
        return service.findById(id);
    }

    @PostMapping(ApiRoutes.CREATE_CITY)
    @ResponseStatus(code = HttpStatus.CREATED)
    public CidadeResponse create(@RequestBody @Valid CidadeRequest request){
        return service.create(request);
    }
    @PutMapping(ApiRoutes.UPDATE_CITY)
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public CidadeResponse update(@PathVariable Long id, @RequestBody @Valid CidadeRequest request){
        return service.update(id, request);
    }      

}
