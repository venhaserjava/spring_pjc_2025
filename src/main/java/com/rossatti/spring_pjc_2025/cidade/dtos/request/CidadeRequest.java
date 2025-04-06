package com.rossatti.spring_pjc_2025.cidade.dtos.request;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import com.rossatti.spring_pjc_2025.cidade.validators.ValidUF;
import com.rossatti.spring_pjc_2025.cidade.validators.UniqueCidade;

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
