package com.rossatti.spring_pjc_2025.commons.handlers;

import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

import java.time.LocalDateTime;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.rossatti.spring_pjc_2025.commons.utils.AnnotationUtils;
import com.rossatti.spring_pjc_2025.commons.dtos.ValidationErrorResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

import jakarta.annotation.Nullable;

@RestControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler{

    private final SnakeCaseStrategy snakeCaseStrategy = new SnakeCaseStrategy();

    @Override  
    @Nullable  
//    @SuppressWarnings("null")
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, 
            HttpStatusCode statusCode, 
            WebRequest request
    ) {

        var status = (HttpStatus) statusCode;
        var errors = new HashMap<String,List<String>>();        
        ex.getBindingResult().getFieldErrors().forEach(error->{
            var fieldName = snakeCaseStrategy.translate(error.getField());            
            var errorMessage = error.getDefaultMessage();            
            if (errors.containsKey(fieldName)){
                errors.get(fieldName).add(errorMessage);
            } else {
                errors.put(fieldName, new ArrayList<String>(Arrays.asList(errorMessage)));
            }
        });    

        var message =   "Erro(s) de Validação ";
        if (status.value()>0 & errors.isEmpty()) {                      

            var generic = ex.getParameter().getGenericParameterType().getTypeName().toString();                            
            var teste = AnnotationUtils.getUniqueCidadeMessage(generic);
            errors.put("", new ArrayList<String>(Arrays.asList(teste)));

        }
        var body = ValidationErrorResponse.builder()
                .status(status.value())    
                .error(status.getReasonPhrase())
                .message(message)
                .cause(ex.getClass().getSimpleName())
                .timestamp(LocalDateTime.now())
                .erros(errors)
                .build();        

        return new ResponseEntity<Object>(body,status);
        
    }
}
