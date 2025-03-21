package com.rossatti.spring_pjc_2025.cidade.exceptions;

public class CidadeFoundException  extends RuntimeException{

    public CidadeFoundException(String message){
        super(message);
    }
    public CidadeFoundException(){
        super("Já existe uma cidade com este nome e UF.");
    }

}
