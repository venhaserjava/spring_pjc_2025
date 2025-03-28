package com.rossatti.spring_pjc_2025.unidade.dtos.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.rossatti.spring_pjc_2025.commons.validators.UniqueValue;
import com.rossatti.spring_pjc_2025.unidade.entities.Unidade;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class UnidadeRequest {

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 200)
    @UniqueValue(entityClass = Unidade.class, fieldName = "nome", message = "Este Nome já está em uso.")   
    private String nome;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 200)
    @UniqueValue(entityClass = Unidade.class, fieldName = "sigla", message = "Esta Sigla já está em uso.")       
    private String sigla;

}
