package com.rossatti.spring_pjc_2025.lotacao.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UnidadeExistsValidator.class)
@Target({ ElementType.FIELD }) // Agora só pode ser aplicada em atributos individuais (como ID)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUnidadeExists {
    String message() default "A unidade informada com ID {value} não existe no banco de dados.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
