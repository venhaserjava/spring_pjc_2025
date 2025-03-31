package com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rossatti.spring_pjc_2025.commons.routes.ApiRoutes;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.request.ServidorTemporarioRequest;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.response.ServidorTemporarioDTO;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.response.ServidorTemporarioResponse;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.service.ServidorTemporarioService;
//import jakarta.validation.Valid;

@RestController
//@RequestMapping("/api/servidores-temporarios")
public class ServidorTemporarioController {

    @Autowired
    private final ServidorTemporarioService servidorTemporarioService;

    public ServidorTemporarioController(ServidorTemporarioService servidorTemporarioService) {
        this.servidorTemporarioService = servidorTemporarioService;
    }

    @GetMapping(ApiRoutes.SERVANT_TEMP_FIND_BY_ID)
    @ResponseStatus(code = HttpStatus.FOUND)
    public ResponseEntity<ServidorTemporarioResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(servidorTemporarioService.findByPessoaId(id));
    }

    @GetMapping(ApiRoutes.SERVANT_TEMP_FIND)
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseEntity<Page<ServidorTemporarioDTO>> findAll(
        @RequestParam(required = false) String nome,
        Pageable pageable
    ) {
        return ResponseEntity.ok( servidorTemporarioService.findAllServidoresTemporarios(nome, pageable) ) ;
    }

    @PostMapping(ApiRoutes.SERVANT_TEMP_CREATE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody ServidorTemporarioRequest dto) {
        servidorTemporarioService.create(dto);
        return ResponseEntity.ok().body("{\"mensagem\": \"Servidor temporário cadastrado com sucesso.\"}");
    }

    @PutMapping(ApiRoutes.SERVANT_TEMP_UPDATE)
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseEntity<?> update(
        @PathVariable Long id,
        @RequestBody ServidorTemporarioRequest request
    ){
        servidorTemporarioService.update(id, request);
        return ResponseEntity.ok().body("{\"mensagem\": \"Servidor temporário Alterado com sucesso.\"}");
    }     
    //  @PutMapping(ApiRoutes.UPDATE_PEOPLE)
    // @ResponseStatus(code = HttpStatus.ACCEPTED)
    // public PessoaResponse update(@PathVariable Long id, @RequestBody @Valid PessoaRequest request){
    //     return service.update(id, request);
    // }
  
    
}
