package com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.controllers;

//import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rossatti.spring_pjc_2025.commons.routes.ApiRoutes;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.services.ServidorEfetivoService;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.dtos.request.ServidorEfetivoRequestDTO;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.dtos.response.ServidorEfetivoResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ServidorEfetivoRestController {

    private final ServidorEfetivoService servidorEfetivoService;
    
    @PostMapping(ApiRoutes.SERVANT_EFFECTIVE_CREATE)
    public ResponseEntity<ServidorEfetivoResponseDTO> criarServidorEfetivo(@RequestBody ServidorEfetivoRequestDTO request) {

        var servidorE = servidorEfetivoService.criarServidorEfetivo(request);        
        return ResponseEntity.ok(servidorE);
    }    

    @GetMapping(ApiRoutes.SERVANT_EFFECTIVE_FIND_UNITY_BY_ID)
    public ResponseEntity<Page<ServidorEfetivoResponseDTO>> listarServantsByUnits(
        @PathVariable Long unidadeId,
        @PageableDefault(size = 10, sort = "nome", direction = Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(servidorEfetivoService.listarServidoresPorUnidade(unidadeId,pageable));
    }
}

