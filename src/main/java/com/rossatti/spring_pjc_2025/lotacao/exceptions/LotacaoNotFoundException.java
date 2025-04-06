package com.rossatti.spring_pjc_2025.lotacao.exceptions;

import com.rossatti.spring_pjc_2025.exceptions.ModelNotFoundException;

public class LotacaoNotFoundException extends ModelNotFoundException {

    public LotacaoNotFoundException(String message) {
        super(message);
    }
    public LotacaoNotFoundException(){
        super("A Lotação não foi encontrada."); 
    }
}
