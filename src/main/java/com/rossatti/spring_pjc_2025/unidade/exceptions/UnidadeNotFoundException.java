package com.rossatti.spring_pjc_2025.unidade.exceptions;

import com.rossatti.spring_pjc_2025.exceptions.ModelNotFoundException;

public class UnidadeNotFoundException extends ModelNotFoundException {

    public UnidadeNotFoundException(String message) {
        super(message);
        //TODO Auto-generated constructor stub
    }
    public UnidadeNotFoundException() {
        super("Unidade não encontrada.");
    }

}
