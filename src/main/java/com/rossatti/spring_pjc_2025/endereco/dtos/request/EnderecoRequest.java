package com.rossatti.spring_pjc_2025.endereco.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoRequest {

    @NotBlank(message = "O tipo de logradouro é obrigatório")
    @Size(max = 50, message = "O tipo de logradouro deve ter no máximo 50 caracteres")
    @Pattern(regexp = "Rua|Avenida|Travessa|Alameda|Praça|Rodovia|Estrada|Aeroporto|Campo|Chácara|Colônia|Condomínio|Conjunto|Distrito|Esplanada|Estação|Fazenda",
             message = "O tipo de logradouro deve ser um dos seguintes: Rua, Avenida, Travessa, Alameda, Praça, Rodovia, Estrada, Aeroporto, Campo, Chácara, Colônia, Condomínio, Conjunto, Distrito, Esplanada, Estação, Fazenda")
    private String tipoLogradouro;    

    @NotBlank(message = "O logradouro é obrigatório")
    @Size(max = 200, message = "O logradouro deve ter no máximo 200 caracteres")
    private String logradouro;
    
    @NotNull(message = "O número é obrigatório")
    @Min(value = 1, message = "O número deve ser maior que zero")
    private Integer numero;
    
    @NotBlank(message = "O bairro é obrigatório")
    @Size(max = 100, message = "O bairro deve ter no máximo 100 caracteres")
    private String bairro;
    
    @NotNull(message = "O ID da cidade é obrigatório")
    private Long cidadeId;

}    



