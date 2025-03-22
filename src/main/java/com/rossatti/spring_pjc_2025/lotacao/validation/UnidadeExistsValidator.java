package com.rossatti.spring_pjc_2025.lotacao.validation;


import com.rossatti.spring_pjc_2025.unidade.repositories.UnidadeRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UnidadeExistsValidator implements ConstraintValidator<ValidUnidadeExists, Long> {

    @Autowired
    private final UnidadeRepository unidadeRepository;

    public UnidadeExistsValidator(UnidadeRepository unidadeRepository) {
        this.unidadeRepository = unidadeRepository;
    }

    @Override
    public boolean isValid(Long unidadeId, ConstraintValidatorContext context) {
        if (unidadeId == null) {
            return false; // ID não pode ser nulo
        }

        boolean exists = unidadeRepository.existsById(unidadeId);
        
        if (!exists) {
            // Personaliza a mensagem de erro
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("A unidade com ID " + unidadeId + " não existe no banco de dados.")
                   .addConstraintViolation();
        }

        return exists;
    }
}
