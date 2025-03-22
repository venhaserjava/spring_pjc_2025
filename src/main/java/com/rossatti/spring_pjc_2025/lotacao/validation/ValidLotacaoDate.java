package com.rossatti.spring_pjc_2025.lotacao.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LotacaoDateValidator.class)
@Target({ ElementType.TYPE }) // A anotação será usada na classe, não no campo
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLotacaoDate {
    String message() default "A data de lotação deve ser posterior à data de nascimento da pessoa.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
