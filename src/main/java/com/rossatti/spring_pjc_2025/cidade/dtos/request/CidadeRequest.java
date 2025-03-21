package com.rossatti.spring_pjc_2025.cidade.dtos.request;

import com.rossatti.spring_pjc_2025.cidade.validators.UniqueCidade;
import com.rossatti.spring_pjc_2025.cidade.validators.ValidUF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@UniqueCidade(message = "Já existe uma cidade com este nome e UF.")
@NoArgsConstructor
@AllArgsConstructor
public class CidadeRequest {

    @Size(min = 3, max = 200)
    @NotBlank(message = "O nome da cidade é obrigatório.")   
    private String nome;

    @Size(min = 2,max = 2)
    @ValidUF
    @NotBlank(message = "A UF é obrigatória.")    
    private String uf;   

    public void setUf(String uf) {
        this.uf = uf != null ? uf.toUpperCase() : null;
    }
}
