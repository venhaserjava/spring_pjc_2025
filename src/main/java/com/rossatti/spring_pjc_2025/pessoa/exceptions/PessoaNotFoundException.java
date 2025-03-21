package com.rossatti.spring_pjc_2025.pessoa.exceptions;

import com.rossatti.spring_pjc_2025.exceptions.ModelNotFoundException;

public class PessoaNotFoundException extends ModelNotFoundException{

    public PessoaNotFoundException(String message) {
        super(message);
    }
    public PessoaNotFoundException() {
        super("Pessoa não encontrada.");
    }    


}
