package com.rossatti.spring_pjc_2025.pessoa.dtos.request;

import java.time.LocalDate;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.commons.validators.UniqueValue;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class PessoaRequest {

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 200)
    @UniqueValue(entityClass = Pessoa.class, fieldName = "nome", message = "Este Nome já está em uso.")       
    private String nome;
    
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 200)
    private String mae;

    @Size(min = 3, max = 200)
    private String pai;

    @NotNull
    @Pattern(regexp = "MASCULINO|FEMININO|NAOINF", message = "Sexo deve ser MASCULINO, FEMININO ou NAOINF")
    private String sexo;
    
    @NotNull    
    @Past
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDate dataNascimento;

}
