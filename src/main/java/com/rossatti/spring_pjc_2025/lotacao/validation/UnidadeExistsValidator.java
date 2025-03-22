package com.rossatti.spring_pjc_2025.lotacao.validation;

import com.rossatti.spring_pjc_2025.unidade.repositories.UnidadeRepository;
import com.rossatti.spring_pjc_2025.unidade.models.Unidade;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UnidadeExistsValidator implements ConstraintValidator<ValidUnidadeExists, Unidade> {

    @Autowired
    private UnidadeRepository unidadeRepository;

    @Override
    public boolean isValid(Unidade unidade, ConstraintValidatorContext context) {
        if (unidade == null || unidade.getId() == null) {
            return false; // Se não foi informada uma unidade válida, falha na validação
        }

        return unidadeRepository.existsById(unidade.getId()); // Verifica se existe no banco
    }
}
