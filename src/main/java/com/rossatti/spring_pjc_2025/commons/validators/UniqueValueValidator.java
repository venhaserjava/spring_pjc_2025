package com.rossatti.spring_pjc_2025.commons.validators;

import com.rossatti.spring_pjc_2025.commons.services.HttpService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueValueValidator implements ConstraintValidator<UniqueValue,String>{

        @PersistenceContext
    private EntityManager entityManager;

    private String fieldName;
    private Class<?> entityClass;

    private final HttpService httpService;


    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.entityClass = constraintAnnotation.entityClass();
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.trim().isEmpty()) {
            return true; // Assume que a verificação de `@NotNull` será feita separadamente
        }
        var id = httpService.getPathVariable("id", Long.class);
        if (id.isEmpty()) {
            String query = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e WHERE e." + fieldName + " = :value";            
            Long count = entityManager.createQuery(query, Long.class)
                                    .setParameter("value", value)
                                    .getSingleResult();                                                
            return count == 0; // Retorna `true` se o valor for único (não estiver no banco)                                                
        } else {
            String key =  id.map(String::valueOf).orElse("0");
            String query = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e WHERE e." + fieldName + " = :value AND e.id <> :key";
            Long count = entityManager.createQuery(query, Long.class)
                                    .setParameter("value", value)
                                    .setParameter("key", key)                                    
                                    .getSingleResult();                                                            
            return count == 0; // Retorna `true` se o valor for único (não estiver no banco)                                                
        }

    }

}
