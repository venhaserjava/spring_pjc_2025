package com.rossatti.spring_pjc_2025.lotacao.validation;


import com.rossatti.spring_pjc_2025.lotacao.dtos.request.LotacaoRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Date;

public class LotacaoDateValidator implements ConstraintValidator<ValidLotacaoDate, LotacaoRequest> {

    @Override
    public boolean isValid(LotacaoRequest request, ConstraintValidatorContext context) {
        if (request.getPessoa() == null || request.getPessoa().getDataNascimento() == null || request.getDataLotacao() == null) {
            return true; // Se os valores forem nulos, não valida (deixe para @NotNull fazer isso)
        }

        Date dataNascimento = request.getPessoa().getDataNascimento();
        Date dataLotacao = request.getDataLotacao();

        return dataLotacao.after(dataNascimento); // Garante que dataLotacao > dataNascimento
    }
}
