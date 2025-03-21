package com.rossatti.spring_pjc_2025.cidade.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueCidadeValidator.class)
@Target({ElementType.TYPE}) // Aplica-se à classe DTO, não a um campo específico
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueCidade {
    String message() default "Já existe uma cidade com este nome e UF.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};    
}