package com.rossatti.spring_pjc_2025.endereco.exceptions;

import com.rossatti.spring_pjc_2025.exceptions.ModelNotFoundException;

public class EnderecoNotFoundException extends ModelNotFoundException {

    public EnderecoNotFoundException(String message) {
        super(message);
        //TODO Auto-generated constructor stub
    }
    public EnderecoNotFoundException() {
        super("Endereço não encontrado!");        //TODO Auto-generated constructor stub
    }
}
