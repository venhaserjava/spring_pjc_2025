package com.rossatti.spring_pjc_2025.servidor.mappers;

import org.springframework.stereotype.Component;

import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.servidor.dtos.request.ServidorRequest;
import com.rossatti.spring_pjc_2025.servidor.dtos.response.ServidorResponse;

@Component
public class ServidorMapperImpl implements ServidorMapper {

    @Override
    public ServidorResponse toResponse(Pessoa model) {

        return ServidorResponse.builder()
                .id(model.getId())
                .nome(model.getNome())
                .mae(model.getMae())
                .pai(model.getPai())
                .dataNascimento(model.getDataNascimento())
                .tipoLogradouro(model.getEnderecos().iterator().next().getTipoLogradouro())
                .logradouro(model.getEnderecos().iterator().next().getLogradouro())
                .numero(model.getEnderecos().iterator().next().getNumero())
                .bairro(model.getEnderecos().iterator().next().getBairro())
                .cidadeNome(model.getEnderecos().iterator().next().getCidade().getNome())
                .fotoUrl("")
                .build();
    }

    @Override
    public Pessoa toModel(ServidorRequest request) {
        return Pessoa.builder()
            .nome(request.getNome())
            .mae(request.getMae())
            .pai(request.getPai())
            .dataNascimento(request.getDataNascimento())
            .build();            
    }

}
