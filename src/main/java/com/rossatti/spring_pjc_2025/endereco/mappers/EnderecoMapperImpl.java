package com.rossatti.spring_pjc_2025.endereco.mappers;

//import java.util.HashSet;

import org.springframework.stereotype.Component;

import com.rossatti.spring_pjc_2025.cidade.entities.Cidade;
import com.rossatti.spring_pjc_2025.endereco.dtos.request.EnderecoRequest;
import com.rossatti.spring_pjc_2025.endereco.dtos.response.EnderecoResponse;
import com.rossatti.spring_pjc_2025.endereco.entity.Endereco;

@Component
public class EnderecoMapperImpl implements EnderecoMapper {


    
    @Override
    public Endereco toModel(EnderecoRequest dto, Cidade cidade) {
        if (dto == null || cidade == null) {
            return null;
        }
//        return new Endereco(null, dto.getTipoLogradouro(), dto.getLogradouro(), dto.getNumero(), dto.getBairro(), cidade,new HashSet<>(),new HashSet<>());
        return new Endereco(null, dto.getTipoLogradouro(), dto.getLogradouro(), dto.getNumero(), dto.getBairro(), cidade,null,null);
        
    //     return Endereco.builder()
    //         .tipoLogradouro(dto.getTipoLogradouro())
    //         .logradouro(dto.getLogradouro())
    //         .numero(dto.getNumero())
    //         .bairro(dto.getBairro())
    //         .cidade(cidade)
    //         .unidades(new HashSet<>()) // Evita null
    //         .build();
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
