package com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.exceptions;

import com.rossatti.spring_pjc_2025.exceptions.ModelNotFoundException;

public class ServidorEfetivoNotFoundException extends ModelNotFoundException {

    public ServidorEfetivoNotFoundException(String message) {
        super(message);        
    }
    
    public ServidorEfetivoNotFoundException() {
        super("Servidor Efetivo não encontrado!");
    }

}
