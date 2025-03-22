package com.rossatti.spring_pjc_2025.lotacao.validation;

import com.rossatti.spring_pjc_2025.pessoa.repositories.PessoaRepository;
import com.rossatti.spring_pjc_2025.pessoa.models.Pessoa;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class PessoaExistsValidator implements ConstraintValidator<ValidPessoaExists, Pessoa> {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public boolean isValid(Pessoa pessoa, ConstraintValidatorContext context) {
        if (pessoa == null || pessoa.getId() == null) {
            return false; // Se não foi informada uma pessoa válida, falha na validação
        }

        return pessoaRepository.existsById(pessoa.getId()); // Verifica se existe no banco
    }
}
