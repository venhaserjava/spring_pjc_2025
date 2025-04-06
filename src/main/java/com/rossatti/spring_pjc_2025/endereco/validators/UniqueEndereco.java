package com.rossatti.spring_pjc_2025.endereco.validators;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import jakarta.validation.Payload;
import jakarta.validation.Constraint;

@Documented
@Constraint(validatedBy = UniqueEnderecoValidator.class)
@Target({ElementType.TYPE}) // lementType.TYPE Aplica-se à classe DTO, não a um campo específico
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEndereco {

    String message() default "Este Endereço Já existe!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};    
    
}