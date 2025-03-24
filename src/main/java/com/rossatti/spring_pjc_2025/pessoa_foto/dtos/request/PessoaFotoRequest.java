package com.rossatti.spring_pjc_2025.pessoa_foto.dtos.request;

import java.time.LocalDate;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaFotoRequest {
    private Long pessoaId;
    private LocalDate data;    
    private String bucket;    
    private String hash;
}



