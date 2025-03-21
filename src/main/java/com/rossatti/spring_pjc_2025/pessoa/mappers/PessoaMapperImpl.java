package com.rossatti.spring_pjc_2025.pessoa.mappers;

import org.springframework.stereotype.Component;

import com.rossatti.spring_pjc_2025.pessoa.dtos.request.PessoaRequest;
import com.rossatti.spring_pjc_2025.pessoa.dtos.response.PessoaResponse;
import com.rossatti.spring_pjc_2025.pessoa.models.Pessoa;

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
            .sexo(model.getSexo()) // ✅ Agora pega direto como String
            .build();        
    }
    
    @Override
    public Pessoa toModel(PessoaRequest request){
        return Pessoa.builder()
            .nome(request.getNome())
            .pai(request.getPai())            
            .mae(request.getMae())
            .sexo(request.getSexo()) // ✅ Agora recebe como String
            .dataNascimento(request.getDataNascimento())
            .build();
    }



}
