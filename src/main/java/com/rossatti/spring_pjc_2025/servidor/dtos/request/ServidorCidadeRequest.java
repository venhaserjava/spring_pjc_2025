package com.rossatti.spring_pjc_2025.servidor.dtos.request;

import com.rossatti.spring_pjc_2025.cidade.validators.ValidUF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServidorCidadeRequest {

    @Size(min = 3, max = 200)
    @NotBlank(message = "O nome da cidade é obrigatório.")   
    private String nome;

    @Size(min = 2,max = 2)
    @ValidUF
    @NotBlank(message = "A UF é obrigatória.")    
    private String uf;
}
