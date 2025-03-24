package com.rossatti.spring_pjc_2025.pessoa_foto.exceptions;

import com.rossatti.spring_pjc_2025.exceptions.ModelNotFoundException;

public class PessoaFotoNotFoundException extends ModelNotFoundException{

        public PessoaFotoNotFoundException(String message) {
            super(message);
        }
        public PessoaFotoNotFoundException() {
            super("Foto não encontrada.");
        }    
}
