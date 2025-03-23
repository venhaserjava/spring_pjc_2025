package com.rossatti.spring_pjc_2025.endereco.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rossatti.spring_pjc_2025.commons.routes.ApiRoutes;
import com.rossatti.spring_pjc_2025.endereco.dtos.request.EnderecoRequest;
import com.rossatti.spring_pjc_2025.endereco.dtos.response.EnderecoResponse;
import com.rossatti.spring_pjc_2025.endereco.services.EnderecoService;

@RestController
//@RequestMapping("/enderecos")
@RequestMapping(ApiRoutes.ADDRESSES)
public class EnderecoRestController {

    private final EnderecoService enderecoService;

    public EnderecoRestController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }
    
    //    @GetMapping("/{id}")
    @GetMapping(ApiRoutes.FIND_ADDRESS_BY_ID)
    public ResponseEntity<EnderecoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(enderecoService.findById(id));
    }
    
    @GetMapping(ApiRoutes.FIND_ADDRESSES)
    public ResponseEntity<Page<EnderecoResponse>> findAll(
        @PageableDefault(size = 10, sort = "logradouro", direction = Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(enderecoService.findAll(pageable));
    }
    
    @PostMapping(ApiRoutes.CREATE_ADDRESS)
    public ResponseEntity<EnderecoResponse> create(@RequestBody EnderecoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.create(dto));
    }
//    @PutMapping("/{id}")
    @PutMapping(ApiRoutes.UPDATE_ADDRESS)
    public ResponseEntity<EnderecoResponse> update(@PathVariable Long id, @RequestBody EnderecoRequest dto) {
        return ResponseEntity.ok(enderecoService.update(id, dto));
    }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deletarEndereco(@PathVariable Long id) {
    //     enderecoService.delete(id);
    //     return ResponseEntity.noContent().build();
    // }
}
