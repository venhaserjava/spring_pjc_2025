package com.rossatti.spring_pjc_2025.commons.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = UniqueValueValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueValue {
    String message() default "O valor já está em uso.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    String fieldName(); // Nome do campo a ser validado
    Class<?> entityClass(); // Classe da entidade
}
