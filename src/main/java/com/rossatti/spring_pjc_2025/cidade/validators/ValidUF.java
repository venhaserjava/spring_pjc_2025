package com.rossatti.spring_pjc_2025.cidade.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = UFValidator.class) // Associa ao validador
@Target({ElementType.FIELD}) // Pode ser usada apenas em campos
@Retention(RetentionPolicy.RUNTIME) // Mantida em tempo de execução
public @interface ValidUF {
    String message() default "UF inválida. Use uma sigla válida de estado brasileiro."; // Mensagem de erro
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

