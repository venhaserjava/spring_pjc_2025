package com.rossatti.spring_pjc_2025.unidade.mappers;

import org.springframework.stereotype.Component;

import com.rossatti.spring_pjc_2025.unidade.dtos.request.UnidadeRequest;
import com.rossatti.spring_pjc_2025.unidade.dtos.response.UnidadeResponse;
import com.rossatti.spring_pjc_2025.unidade.entities.Unidade;

@Component
public class UnidadeMapperImpl implements UnidadeMapper {
    
    @Override
    public UnidadeResponse toResponse(Unidade model) {

        if (model==null) {
            return null;            
        }        
        // return UnidadeResponse.builder()
        //     .id(model.getId())
        //     .nome(model.getNome())
        //     .sigla(model.getSigla())
        //     .build();        
        return UnidadeResponse.builder()
                .id(model.getId())
                .nome(model.getNome())
                .sigla(model.getSigla())
                .tipoLogradouro(model.getEnderecos().iterator().next().getTipoLogradouro())
                .logradouro(model.getEnderecos().iterator().next().getLogradouro())
                .numero(model.getEnderecos().iterator().next().getNumero())
                .bairro(model.getEnderecos().iterator().next().getBairro())
                .cidadeNome(
                    model.getEnderecos().iterator().next().getCidade().getNome()
                    +" - "+
                    model.getEnderecos().iterator().next().getCidade().getUf())                
                .build();
    }
    @Override
    public Unidade toModel(UnidadeRequest request){
        return Unidade.builder()        
        .nome(request.getNome())
        .sigla(request.getSigla())
        .build();
    }

}
