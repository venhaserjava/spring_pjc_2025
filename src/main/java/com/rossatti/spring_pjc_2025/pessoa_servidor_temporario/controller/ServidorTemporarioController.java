package com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.request.ServidorTemporarioRequest;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.service.ServidorTemporarioService;

@RestController
@RequestMapping("/api/servidores-temporarios")
public class ServidorTemporarioController {

    @Autowired
    private final ServidorTemporarioService servidorTemporarioService;

    public ServidorTemporarioController(ServidorTemporarioService servidorTemporarioService) {
        this.servidorTemporarioService = servidorTemporarioService;
    }

    @PostMapping
    public ResponseEntity<?> cadastrarServidorTemporario(@RequestBody ServidorTemporarioRequest dto) {
        servidorTemporarioService.cadastrarServidorTemporario(dto);
        return ResponseEntity.ok().body("{\"mensagem\": \"Servidor temporário cadastrado com sucesso.\"}");
    }
}
