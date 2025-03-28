package com.rossatti.spring_pjc_2025.pessoa_foto.mappers;

import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa_foto.dtos.request.PessoaFotoRequest;
import com.rossatti.spring_pjc_2025.pessoa_foto.dtos.response.PessoaFotoResponse;
import com.rossatti.spring_pjc_2025.pessoa_foto.models.PessoaFoto;

public interface PessoaFotoMapper {

    public PessoaFoto toModel(PessoaFotoRequest request,Pessoa pessoa);
    public PessoaFotoResponse toResponse(PessoaFoto pessoaFoto);

}
