package com.rossatti.spring_pjc_2025.endereco.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEnderecoValidator.class)
@Target({ElementType.TYPE}) // Aplica-se à classe DTO, não a um campo específico
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEndereco {
    String message() default "Este Endereço Já existe!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};    
}