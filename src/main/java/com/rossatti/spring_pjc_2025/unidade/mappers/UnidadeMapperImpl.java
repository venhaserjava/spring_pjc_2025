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
        UnidadeResponse unidadeResponse = UnidadeResponse.builder()
                .id(model.getId())
                .nome(model.getNome())
                .sigla(model.getSigla())                
                .tipoLogradouro(model.getUnidadeEndereco().getEndereco().getTipoLogradouro())
                .logradouro(model.getUnidadeEndereco().getEndereco().getLogradouro())
                .numero(model.getUnidadeEndereco().getEndereco().getNumero())
                .bairro(model.getUnidadeEndereco().getEndereco().getBairro())
                .cidadeNome(
                        model.getUnidadeEndereco().getEndereco().getCidade().getNome()
                    +" - "+
                        model.getUnidadeEndereco().getEndereco().getCidade().getUf()
                )
                .build();
        return  unidadeResponse;      
    }
    @Override
    public Unidade toModel(UnidadeRequest request){
        return Unidade.builder()        
        .nome(request.getNome())
        .sigla(request.getSigla())
        .build();
    }

}
