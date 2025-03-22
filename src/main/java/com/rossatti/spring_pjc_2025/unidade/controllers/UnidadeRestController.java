package com.rossatti.spring_pjc_2025.unidade.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rossatti.spring_pjc_2025.commons.routes.ApiRoutes;
import com.rossatti.spring_pjc_2025.unidade.dtos.request.UnidadeRequest;
import com.rossatti.spring_pjc_2025.unidade.dtos.response.UnidadeResponse;
import com.rossatti.spring_pjc_2025.unidade.services.UnidadeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
//@RequestMapping(ApiRoutes.UNITS)
public class UnidadeRestController {

    private final UnidadeService service;
    
    @GetMapping(ApiRoutes.FIND_UNITS)
    public Page<UnidadeResponse> findUnits(
        @RequestParam(name = "q",required = false,defaultValue = "") String unity,
        @PageableDefault(size = 10, sort = "nome", direction = Direction.ASC) Pageable pageable
    ) {
        return  service.findUnits(unity,pageable);
    }

    @GetMapping(ApiRoutes.FIND_UNIT_BY_ID)    
    public UnidadeResponse findUnitById(@PathVariable Long id){
        return service.findUnitById(id);
    }

    @PostMapping(ApiRoutes.CREATE_UNIT)
    @ResponseStatus(code = HttpStatus.CREATED )
    public UnidadeResponse create(@RequestBody @Valid UnidadeRequest request){
        return service.create(request);
    }

    @PutMapping(ApiRoutes.UPDATE_UNIT)
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public UnidadeResponse update(@PathVariable Long id,
        @RequestBody @Valid UnidadeRequest request
    ){
        return service.update(id, request);
    }       
}
