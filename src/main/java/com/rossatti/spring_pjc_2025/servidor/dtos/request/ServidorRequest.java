package com.rossatti.spring_pjc_2025.servidor.dtos.request;

import lombok.*;
import java.time.LocalDate;
import java.util.Set;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class ServidorRequest {
        private String nome;
        private String mae;
        private String pai;
        private String sexo;
        private LocalDate dataNascimento;
        private Set<ServidorEnderecoRequest> enderecos;
    }
    

