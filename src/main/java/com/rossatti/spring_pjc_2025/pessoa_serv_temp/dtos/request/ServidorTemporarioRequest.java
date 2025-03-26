package com.rossatti.spring_pjc_2025.pessoa_serv_temp.dtos.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServidorTemporarioRequest {

    @NotNull(message = "O ID da pessoa é obrigatório.")
    private Long pessoaId;

    @NotNull(message = "A data de admissão é obrigatória.")
    private LocalDate dataAdmissao;

    @NotNull(message = "Os dados de lotação são obrigatórios.")
    private LotacaoDTO lotacao;


}
