package com.rossatti.spring_pjc_2025.cidade.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rossatti.spring_pjc_2025.cidade.dtos.request.CidadeRequest;
import com.rossatti.spring_pjc_2025.cidade.repositories.CidadeRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UniqueCidadeValidator implements ConstraintValidator<UniqueCidade,CidadeRequest> {

    @Autowired
    private CidadeRepository cidadeRepository;
    
    // public UniqueCidadeValidator(CidadeRepository cidadeRepository) {
    //     this.cidadeRepository = cidadeRepository;
    // }


    @Override
    public boolean isValid(CidadeRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true; // Não valida objetos nulos
        }
        return !cidadeRepository.existsByNomeAndUf(request.getNome(), request.getUf());
    }
}
