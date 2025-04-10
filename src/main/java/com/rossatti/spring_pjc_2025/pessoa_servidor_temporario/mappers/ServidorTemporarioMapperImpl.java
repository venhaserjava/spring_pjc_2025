package com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.mappers;

import org.springframework.stereotype.Component;
import com.rossatti.spring_pjc_2025.endereco.entities.Endereco;
import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.response.ServidorTemporarioDTO;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.response.ServidorTemporarioResponse;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.entities.ServidorTemporario;

@Component
public class ServidorTemporarioMapperImpl implements ServidorTemporarioMapper {

    @Override
    public ServidorTemporarioResponse toResponse(Pessoa model, ServidorTemporario servidor) {
        return ServidorTemporarioResponse.builder()
            .pessoaId(model.getId())
            .pessoaNome(model.getNome())
            .pessoaMae(model.getMae())
            .pessoaPai(model.getPai())
            .sexo(model.getSexo())
            .dataNascimento(model.getDataNascimento())
            .TipoServidor("Servidor Temporario")
            .dataAdmissao(servidor.getDataAdmissao())
            .dataDemissao(servidor.getDataDemissao())
            .build();
    }  

    @Override
    public ServidorTemporarioDTO mapToDTO(ServidorTemporario servidorTemporario) {
        Pessoa pessoa = servidorTemporario.getPessoa();
        
        // Verifica se a pessoa tem endereços antes de acessar
        Endereco endereco = (pessoa.getEnderecos() != null && !pessoa.getEnderecos().isEmpty()) 
            ? pessoa.getEnderecos().iterator().next() 
            : null;

        return new ServidorTemporarioDTO(
            pessoa.getId(),
            pessoa.getNome(),
            pessoa.getMae(),
            pessoa.getPai(),
            pessoa.getSexo(),
            pessoa.getDataNascimento(),                        
            (endereco != null) ? endereco.getTipoLogradouro() : null,
            (endereco != null) ? endereco.getLogradouro() : null,
            (endereco != null) ? endereco.getNumero() : null,
            (endereco != null) ? endereco.getBairro() : null,
            (endereco != null && endereco.getCidade() != null) ? endereco.getCidade().getNome() : null,
            "Servidor_Temporário",
            servidorTemporario.getDataAdmissao(),
            servidorTemporario.getDataDemissao()
        );
    }   
}
