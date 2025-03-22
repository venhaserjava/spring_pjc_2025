package com.rossatti.spring_pjc_2025.lotacao.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UnidadeExistsValidator.class)
@Target({ ElementType.FIELD }) // A anotação será aplicada em um campo
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUnidadeExists {
    String message() default "A unidade informada não existe no banco de dados.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
