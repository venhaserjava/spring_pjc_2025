package com.rossatti.spring_pjc_2025.servidor.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rossatti.spring_pjc_2025.commons.routes.ApiRoutes;
import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa.services.PessoaService;
import com.rossatti.spring_pjc_2025.pessoa_foto.services.PessoaFotoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ServidorRestController {

    private final PessoaService service;
    private final PessoaFotoService pessoaFotoService;

       @PostMapping(path = ApiRoutes.CREATE_PEOPLE,consumes = {"multipart/form-data"})
    public ResponseEntity<EntityModel<Pessoa>> create(
            @RequestPart("pessoa") Pessoa pessoa,
            @RequestPart("foto") MultipartFile foto) throws IOException {
                
            }


}
