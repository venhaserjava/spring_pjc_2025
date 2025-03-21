package com.rossatti.spring_pjc_2025.cidade.exceptions;

import com.rossatti.spring_pjc_2025.exceptions.ModelNotFoundException;

public class CidadeNotFoundException extends ModelNotFoundException {
    
    public CidadeNotFoundException(String message) {
        super(message);        
    }
    public CidadeNotFoundException(){
        super("Cidade não Encontrada");
    }

}
