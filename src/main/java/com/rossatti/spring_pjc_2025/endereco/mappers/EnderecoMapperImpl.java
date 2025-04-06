package com.rossatti.spring_pjc_2025.endereco.mappers;

import java.util.HashSet;

//import java.util.HashSet;

import org.springframework.stereotype.Component;

import com.rossatti.spring_pjc_2025.cidade.entities.Cidade;
import com.rossatti.spring_pjc_2025.endereco.dtos.request.EnderecoRequest;
import com.rossatti.spring_pjc_2025.endereco.dtos.response.EnderecoResponse;
import com.rossatti.spring_pjc_2025.endereco.entities.Endereco;

@Component
public class EnderecoMapperImpl implements EnderecoMapper {


    
    @Override
    public Endereco toModel(
        EnderecoRequest dto, 
        Cidade cidade
    ) {

        if (dto == null || cidade == null) {
            return null;
        }
        
        return Endereco.builder()
            .tipoLogradouro(dto.getTipoLogradouro())
            .logradouro(dto.getLogradouro())
            .numero(dto.getNumero())
            .bairro(dto.getBairro())
            .cidade(cidade)
            .unidadeEnderecos(new HashSet<>()) 
            .build();
     }

    @Override
    public EnderecoResponse toResponse(Endereco endereco) {
        
        if (endereco == null) {
            return null;
        }
        return new EnderecoResponse(
            endereco.getId(),
            endereco.getTipoLogradouro(),
            endereco.getLogradouro(),
            endereco.getNumero(),
            endereco.getBairro(),
            endereco.getCidade().getNome()
        );
    }
}
