package com.rossatti.spring_pjc_2025.endereco.mappers;

import com.rossatti.spring_pjc_2025.cidade.models.Cidade;
import com.rossatti.spring_pjc_2025.endereco.dtos.request.EnderecoRequest;
import com.rossatti.spring_pjc_2025.endereco.dtos.response.EnderecoResponse;
import com.rossatti.spring_pjc_2025.endereco.models.Endereco;

public class EnderecoMapperImpl implements EnderecoMapper {

    @Override
    public Endereco toModel(EnderecoRequest dto, Cidade cidade) {
        if (dto == null || cidade == null) {
            return null;
        }
        return new Endereco(null, dto.getTipoLogradouro(), dto.getLogradouro(), dto.getNumero(), dto.getBairro(), cidade);
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
