package com.rossatti.spring_pjc_2025.lotacao.controllers;


import com.rossatti.spring_pjc_2025.commons.routes.ApiRoutes;
import com.rossatti.spring_pjc_2025.lotacao.dtos.request.LotacaoRequest;
import com.rossatti.spring_pjc_2025.lotacao.dtos.response.LotacaoResponse;
import com.rossatti.spring_pjc_2025.lotacao.services.LotacaoService;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.dtos.response.EnderecoFuncionalResponseDTO;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping(ApiRoutes.POSTING)
public class LotacaoRestController {

    private final LotacaoService lotacaoService;

    public LotacaoRestController(LotacaoService lotacaoService) {
        this.lotacaoService = lotacaoService;
    }

    // Criar uma nova lotação
//    @PostMapping
    @PostMapping(ApiRoutes.CREATE_POSTING)
    public ResponseEntity<LotacaoResponse> create(@Valid @RequestBody LotacaoRequest request) {
        LotacaoResponse response = lotacaoService.create(request);
        return ResponseEntity.ok(response);
    }

    // Atualizar uma lotação existente
//    @PutMapping("/{id}")
    @PutMapping(ApiRoutes.UPDATE_POSTING)
    public ResponseEntity<LotacaoResponse> update(@PathVariable Long id, @Valid @RequestBody LotacaoRequest request) {
        LotacaoResponse response = lotacaoService.update(id, request);
        return ResponseEntity.ok(response);
    }

    // Buscar uma lotação por ID
//    @GetMapping("/{id}")
    @GetMapping(ApiRoutes.FIND_POSTING_BY_ID)
    public ResponseEntity<LotacaoResponse> findPostingById(@PathVariable Long id) {
        LotacaoResponse response = lotacaoService.findById(id);
        return ResponseEntity.ok(response);
    }

    // Listar todas as lotações
    @GetMapping(ApiRoutes.FIND_POSTING)
    public ResponseEntity<Page<LotacaoResponse>> findAll(
//        @RequestParam(name = "nome",required = false,defaultValue = "") String nome,
        @PageableDefault(size = 10, sort = "portaria", direction = Direction.ASC) Pageable pageable
    ) {
        Page<LotacaoResponse> responses = lotacaoService.findAll(pageable);
        return ResponseEntity.ok(responses);
    }

    // Deletar uma lotação por ID
    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> delete(@PathVariable Long id) {
    //     lotacaoService.delete(id);
    //     return ResponseEntity.noContent().build();
    // }

//    @GetMapping("/endereco-funcional")
    @GetMapping(ApiRoutes.FUNCTIONAL_ADDRESS)
    public ResponseEntity<Page<EnderecoFuncionalResponseDTO>> buscarEnderecoFuncional(
        @RequestParam String nome,
        @PageableDefault(size = 10, sort = "pessoa.nome", direction = Direction.ASC) Pageable pageable
    ) {
        Page<EnderecoFuncionalResponseDTO> enderecos = lotacaoService.buscarEnderecoFuncionalPorNome(nome, pageable);
        return ResponseEntity.ok(enderecos);
    }


}
