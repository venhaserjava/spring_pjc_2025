package com.rossatti.spring_pjc_2025.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ModelNotFoundException  extends RuntimeException {

    public ModelNotFoundException(String message){
        super(message);
    }

    @SuppressWarnings("null")    
    @ExceptionHandler(ModelNotFoundException.class)
     public ResponseEntity<ErrorResponse> handleModelNotFoundException(
         WebRequest request
     ) {
             var status = HttpStatus.NOT_FOUND;
             var body = ErrorResponse.builder(getCause(),null, getLocalizedMessage())
                // .status(status)
                // .error(status.getReasonPhrase())
                // .message("Erro de Validação.: "+getLocalizedMessage())
                // .cause(getClass().getSimpleName())
                // .timestamp(LocalDateTime.now())
                 .build();
             return new ResponseEntity<ErrorResponse>(body,status);        
        }



}
