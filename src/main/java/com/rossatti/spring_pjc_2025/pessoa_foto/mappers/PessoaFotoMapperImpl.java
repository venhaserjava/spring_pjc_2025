package com.rossatti.spring_pjc_2025.pessoa_foto.mappers;

import org.springframework.stereotype.Component;

import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa_foto.dtos.request.PessoaFotoRequest;
import com.rossatti.spring_pjc_2025.pessoa_foto.dtos.response.PessoaFotoResponse;
import com.rossatti.spring_pjc_2025.pessoa_foto.entities.PessoaFoto;

@Component
public class PessoaFotoMapperImpl implements PessoaFotoMapper {

    @Override
    public PessoaFoto toModel(PessoaFotoRequest request, Pessoa pessoa) {
        return new PessoaFoto(null, pessoa, request.getData(), request.getBucket(), request.getHash());        
    }

    @Override
    public PessoaFotoResponse toResponse(PessoaFoto pessoaFoto) {
        return new PessoaFotoResponse(pessoaFoto.getId(), pessoaFoto.getData(), pessoaFoto.getBucket(), pessoaFoto.getHash());   
    }

}
