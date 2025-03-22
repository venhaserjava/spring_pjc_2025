package com.rossatti.spring_pjc_2025.lotacao.validation;


import com.rossatti.spring_pjc_2025.lotacao.dtos.request.LotacaoRequest;
import com.rossatti.spring_pjc_2025.pessoa.models.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa.repositories.PessoaRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;

public class LotacaoDateValidator implements ConstraintValidator<ValidLotacaoDate, LotacaoRequest> {

    @Autowired
    private final PessoaRepository pessoaRepository;

    public LotacaoDateValidator(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public boolean isValid(LotacaoRequest request, ConstraintValidatorContext context) {
        if (request == null || request.getPessoaId() == null || request.getDataLotacao() == null) {
            return false; // Se o ID da pessoa ou a data de lotação for nulo, a validação falha
        }

        Optional<Pessoa> pessoaOpt = pessoaRepository.findById(request.getPessoaId());
        if (pessoaOpt.isEmpty()) {
            return false; // Se a pessoa não existe, falha na validação
        }

        LocalDate dataNascimento = pessoaOpt.get().getDataNascimento();
        LocalDate dataLotacao = request.getDataLotacao();

        boolean isValid = dataLotacao.isAfter(dataNascimento); // A data de lotação deve ser posterior ao nascimento

        if (!isValid) {
            // Personaliza a mensagem de erro
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                "A data de lotação (" + dataLotacao + ") deve ser posterior à data de nascimento da pessoa (" + dataNascimento + ")."
            ).addConstraintViolation();
        }

        return isValid;
    }
}
