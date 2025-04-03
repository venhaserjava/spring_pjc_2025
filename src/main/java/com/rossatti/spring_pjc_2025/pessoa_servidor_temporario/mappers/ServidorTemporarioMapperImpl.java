package com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.mappers;

import org.springframework.stereotype.Component;

import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.response.ServidorTemporarioResponse;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.entities.ServidorTemporario;

@Component
public class ServidorTemporarioMapperImpl implements ServidorTemporarioMapper {

    @Override
    public ServidorTemporarioResponse toResponse(Pessoa model, ServidorTemporario servidor) {
        return ServidorTemporarioResponse.builder()
            .pessoaId(model.getId())
            .pessoaNome(model.getNome())
            .pessoaMae(model.getMae())
            .pessoaPai(model.getPai())
            .sexo(model.getSexo())
            .dataNascimento(model.getDataNascimento())
            .TipoServidor("Servidor Temporario")
            .dataAdmissao(servidor.getDataAdmissao())
            .dataDemissao(servidor.getDataDemissao())
            .build();
    }   
}
