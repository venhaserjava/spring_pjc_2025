package com.rossatti.spring_pjc_2025.pessoa.mappers;

import java.util.HashSet;

import org.springframework.stereotype.Component;

import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa.dtos.request.PessoaRequest;
import com.rossatti.spring_pjc_2025.pessoa.dtos.response.PessoaResponse;

@Component
public class PessoaMapperImpl implements PessoaMapper {

    @Override
    public PessoaResponse toResponse(Pessoa model) {

        return PessoaResponse.builder()
            .id(model.getId())
            .nome(model.getNome())
            .dataNascimento(model.getDataNascimento())
            .mae(model.getMae())
            .pai(model.getPai())        
            .sexo(model.getSexo()) 
            .build();        
    }
    
    @Override
    public Pessoa toModel(PessoaRequest request){

        return Pessoa.builder()
            .nome(request.getNome())
            .pai(request.getPai())            
            .mae(request.getMae())
            .sexo(request.getSexo()) 
            .dataNascimento(request.getDataNascimento())
            .enderecos(new HashSet<>())
            .build();
    }
}
