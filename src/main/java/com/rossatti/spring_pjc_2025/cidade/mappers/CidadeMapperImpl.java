package com.rossatti.spring_pjc_2025.cidade.mappers;

import org.springframework.stereotype.Component;

import com.rossatti.spring_pjc_2025.cidade.dtos.request.CidadeRequest;
import com.rossatti.spring_pjc_2025.cidade.dtos.response.CidadeResponse;
import com.rossatti.spring_pjc_2025.cidade.models.Cidade;

@Component
public class CidadeMapperImpl implements CidadeMapper{

    @Override
    public CidadeResponse toResponse(Cidade model) {
        return CidadeResponse.builder()
            .id(model.getId())
            .nome(model.getNome())
            .uf(model.getUf())
            .build();
    }

    @Override
    public Cidade toModel(CidadeRequest request) {        
        return Cidade.builder()
            .nome(request.getNome())
            .uf(request.getUf())
            .build();
    }



}
