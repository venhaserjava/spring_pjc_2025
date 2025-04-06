package com.rossatti.spring_pjc_2025.endereco.validators;

import org.springframework.stereotype.Component;

import com.rossatti.spring_pjc_2025.cidade.entities.Cidade;
import com.rossatti.spring_pjc_2025.cidade.repositories.CidadeRepository;
import com.rossatti.spring_pjc_2025.cidade.exceptions.CidadeNotFoundException;

import com.rossatti.spring_pjc_2025.endereco.dtos.request.EnderecoRequest;
import com.rossatti.spring_pjc_2025.endereco.repositories.EnderecoRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UniqueEnderecoValidator  implements ConstraintValidator<UniqueEndereco,EnderecoRequest> {

    private final CidadeRepository cidadeRepository;
    private final EnderecoRepository repository;
    
    @Override
    public void initialize(UniqueEndereco constraintAnnotation) {
    }    

    @Override
    public boolean isValid(EnderecoRequest request,ConstraintValidatorContext context){

        if(request == null){
            return true;
        }

        if (!cidadeRepository.existsById(request.getCidadeId())) {
            return true;            
        }

        Cidade cidade = cidadeRepository.findById(request.getCidadeId())
                            .orElseThrow(CidadeNotFoundException::new);

        
        return !repository.existsByLogradouroAndNumeroAndBairroAndCidade(
            request.getLogradouro(),
            request.getNumero(),
            request.getBairro(),
            cidade);
    }
}