package com.rossatti.spring_pjc_2025.cidade.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CidadeResponse {

    private Long id;
    private String nome;
    private String uf;

}
