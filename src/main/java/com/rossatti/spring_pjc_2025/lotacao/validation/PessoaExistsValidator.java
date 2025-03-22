package com.rossatti.spring_pjc_2025.lotacao.validation;

import com.rossatti.spring_pjc_2025.pessoa.repositories.PessoaRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class PessoaExistsValidator implements ConstraintValidator<ValidPessoaExists, Long> {
    
    @Autowired
    private final PessoaRepository pessoaRepository;

    public PessoaExistsValidator(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public boolean isValid(Long pessoaId, ConstraintValidatorContext context) {
        if (pessoaId == null) {
            return false; // ID não pode ser nulo
        }

        boolean exists = pessoaRepository.existsById(pessoaId);
        
        if (!exists) {
            // Personaliza a mensagem de erro
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("A pessoa com ID " + pessoaId + " não existe no banco de dados.")
                   .addConstraintViolation();
        }

        return exists;
    }
}

