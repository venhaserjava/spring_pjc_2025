package com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.exceptions;

import com.rossatti.spring_pjc_2025.exceptions.ModelNotFoundException;

public class ServidorEfetivoNotFoundException extends ModelNotFoundException {

    public ServidorEfetivoNotFoundException(String message) {
        super(message);
        //TODO Auto-generated constructor stub
    }
    public ServidorEfetivoNotFoundException() {
        super("Servidor Efetivo não encontrado!");        //TODO Auto-generated constructor stub
    }

}
