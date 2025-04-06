package com.rossatti.spring_pjc_2025.lotacao.controllers;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.rossatti.spring_pjc_2025.commons.routes.ApiRoutes;
import com.rossatti.spring_pjc_2025.lotacao.services.LotacaoService;
import com.rossatti.spring_pjc_2025.lotacao.dtos.request.LotacaoRequest;
import com.rossatti.spring_pjc_2025.lotacao.dtos.response.LotacaoResponse;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.dtos.response.EnderecoFuncionalResponseDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.web.PageableDefault;

@RestController
public class LotacaoRestController {

    private final LotacaoService lotacaoService;

    public LotacaoRestController(LotacaoService lotacaoService) {
        this.lotacaoService = lotacaoService;
    }

    @PostMapping(ApiRoutes.CREATE_POSTING)
    public ResponseEntity<LotacaoResponse> create(@Valid @RequestBody LotacaoRequest request) {
        LotacaoResponse response = lotacaoService.create(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping(ApiRoutes.UPDATE_POSTING)
    public ResponseEntity<LotacaoResponse> update(
        @PathVariable Long id, 
        @Valid @RequestBody LotacaoRequest request
    ) {

        LotacaoResponse response = lotacaoService.update(id, request);
        return ResponseEntity.ok(response);

    }

    @GetMapping(ApiRoutes.FIND_POSTING_BY_ID)
    public ResponseEntity<LotacaoResponse> findPostingById(@PathVariable Long id) {
        LotacaoResponse response = lotacaoService.findById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping(ApiRoutes.FIND_POSTING)
    public ResponseEntity<Page<LotacaoResponse>> findAll(
        @PageableDefault(size = 10, sort = "portaria", direction = Direction.ASC) Pageable pageable
    ) {
        Page<LotacaoResponse> responses = lotacaoService.findAll(pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping(ApiRoutes.FUNCTIONAL_ADDRESS)
    public ResponseEntity<Page<EnderecoFuncionalResponseDTO>> buscarEnderecoFuncional(
        @RequestParam String nome,
        @PageableDefault(size = 10, sort = "pessoa.nome", direction = Direction.ASC) Pageable pageable
    ) {

        Page<EnderecoFuncionalResponseDTO> enderecos = lotacaoService.findFunctionalAddressByNome(nome, pageable);
        return ResponseEntity.ok(enderecos);

    }


}
