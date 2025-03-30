package com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.request.ServidorTemporarioRequest;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.service.ServidorTemporarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/servidores-temporarios")
public class ServidorTemporarioController {

    @Autowired
    private final ServidorTemporarioService servidorTemporarioService;

    public ServidorTemporarioController(ServidorTemporarioService servidorTemporarioService) {
        this.servidorTemporarioService = servidorTemporarioService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> cadastrarServidorTemporario(@RequestBody ServidorTemporarioRequest dto) {
        servidorTemporarioService.cadastrarServidorTemporario(dto);
        return ResponseEntity.ok().body("{\"mensagem\": \"Servidor temporário cadastrado com sucesso.\"}");
    }
    @PutMapping
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseEntity<?> update(@PathVariable Long id,
        @RequestBody @Valid ServidorTemporarioRequest request
    ){
        servidorTemporarioService.update(id, request);
        return ResponseEntity.ok().body("{\"mensagem\": \"Servidor temporário Alterado com sucesso.\"}");
    }       
    
}
