package com.rossatti.spring_pjc_2025.servidor.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rossatti.spring_pjc_2025.commons.routes.ApiRoutes;
import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa.services.PessoaService;
import com.rossatti.spring_pjc_2025.pessoa_foto.services.PessoaFotoService;
import com.rossatti.spring_pjc_2025.servidor.dtos.request.ServidorRequest;
import com.rossatti.spring_pjc_2025.servidor.dtos.response.ServidorResponse;
import com.rossatti.spring_pjc_2025.servidor.services.ServidorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ServidorRestController {

   private final PessoaService service;
   private final ServidorService servidorService;
   private final PessoaFotoService pessoaFotoService;

   @PostMapping(path = ApiRoutes.CREATE_PEOPLE,consumes = {"multipart/form-data"})
   public ResponseEntity<ServidorRequest> create(
            @RequestPart("pessoa") ServidorRequest servidor,
            @RequestPart("foto") MultipartFile foto) throws IOException 
            
   {
      ServidorResponse novaPessoa = servidorService.create(servidor);
//      pessoaFotoService.salvarFoto(novaPessoa, foto);

      // EntityModel<PessoaDTO> resource = EntityModel.of(novaPessoa);
      // resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PessoaController.class)
      //            .criarPessoa(pessoaDTO, foto)).withSelfRel());

      //  return ResponseEntity.status(HttpStatus.CREATED).body(resource);
      return ResponseEntity.ok().build();
   }


}
