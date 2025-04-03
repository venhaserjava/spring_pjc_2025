package com.rossatti.spring_pjc_2025.servidor_temporario.services;

import org.mockito.Mock;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.time.LocalDate;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.server.ResponseStatusException;

import com.rossatti.spring_pjc_2025.cidade.entities.Cidade;
import com.rossatti.spring_pjc_2025.endereco.entities.Endereco;
import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa.repositories.PessoaRepository;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.repositories.ServidorEfetivoRepository;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.request.ServidorTemporarioRequest;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.response.ServidorTemporarioDTO;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.response.ServidorTemporarioResponse;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.entities.ServidorTemporario;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.mappers.ServidorTemporarioMapper;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.repositories.ServidorTemporarioRepository;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.service.ServidorTemporarioServiceImpl;

@ExtendWith(MockitoExtension.class)
class ServidorTemporarioServiceTest {

    @InjectMocks
    private ServidorTemporarioServiceImpl servidorTemporarioService;

    @Mock
    private PessoaRepository pessoaRepository;
    
    @Mock
    private ServidorTemporarioRepository servidorTemporarioRepository;

    @Mock
    private ServidorEfetivoRepository servidorEfetivoRepository;

    @Mock
    private ServidorTemporarioMapper servidorTemporarioMapper;

    private Cidade cidade;

    private Endereco endereco;

    @Test
    void deveRetornarServidorTemporarioResponse_QuandoPessoaExisteEServidorAtivo() {

        cidade = Cidade.builder()
                    .id(1L)
                    .nome("São Paulo")
                    .uf("SP")
                    .build();
                    
        endereco = Endereco.builder()
                    .id(1L)
                    .tipoLogradouro("Rua")
                    .logradouro("Av. Paulista")
                    .numero(100)
                    .bairro("Centro")
                    .cidade(cidade)
                    .build();        
        // ARRANGE
        Long pessoaId = 1L;
        Pessoa pessoa = Pessoa.builder()
                .id(pessoaId)
                .nome("Carlos Silva")
                .mae("Maria Silva")
                .sexo("Masculino")
                .dataNascimento(LocalDate.of(1990, 5, 10))
                .enderecos(new HashSet<>(Set.of(endereco))) 
                .build();

        ServidorTemporario servidor = ServidorTemporario.builder()
                .id(10L)
                .pessoaId(pessoaId)
                .dataAdmissao(LocalDate.of(2023, 1, 10))
                .build();

        ServidorTemporarioResponse responseEsperado = ServidorTemporarioResponse.builder()
                .pessoaId(pessoaId)
                .pessoaNome("Carlos Silva")
                .sexo("Masculino")
                .TipoServidor("Servidor Temporário")
                .dataAdmissao(LocalDate.of(2023, 1, 10))
                .build();

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoa));
        when(servidorTemporarioRepository.findByPessoaIdAndDataDemissaoIsNull(pessoaId)).thenReturn(Optional.of(servidor));
        when(servidorTemporarioMapper.toResponse(pessoa, servidor)).thenReturn(responseEsperado);

        // ACT
        ServidorTemporarioResponse response = servidorTemporarioService.findByPessoaId(pessoaId);

        // ASSERT
        assertThat(response).isNotNull();
        assertThat(response.getPessoaNome()).isEqualTo("Carlos Silva");
        assertThat(response.getTipoServidor()).isEqualTo("Servidor Temporário");
    }

    @Test
    void deveRetornarVazio_QuandoPessoaNaoExisteOuNaoTemServidorTemporario() {
        // ARRANGE
        Long pessoaId = 1L;

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.empty());
        when(servidorTemporarioRepository.findByPessoaIdAndDataDemissaoIsNull(pessoaId)).thenReturn(Optional.empty());

        // ACT
        ServidorTemporarioResponse response = servidorTemporarioService.findByPessoaId(pessoaId);

        // ASSERT
        assertThat(response).isNotNull();
        assertThat(response.getPessoaNome()).isNull();
    }

    @Test
    void deveLancarExcecao_QuandoCriarServidorTemporarioComPessoaInexistente() {
        // ARRANGE
        ServidorTemporarioRequest request = ServidorTemporarioRequest.builder()
                .pessoaId(99L)
                .dataAdmissao(LocalDate.of(2024, 1, 1))
                .build();

        when(pessoaRepository.findById(request.getPessoaId())).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> servidorTemporarioService.create(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Pessoa não encontrada.");
    }

    @Test
    void deveLancarExcecao_QuandoCriarServidorTemporarioParaServidorEfetivo() {
        // ARRANGE
        Long pessoaId = 1L;
        ServidorTemporarioRequest request = ServidorTemporarioRequest.builder()
                .pessoaId(pessoaId)
                .dataAdmissao(LocalDate.of(2024, 1, 1))
                .build();

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(new Pessoa()));
        when(servidorEfetivoRepository.existsByPessoaId(pessoaId)).thenReturn(true);

        // ACT & ASSERT
        assertThatThrownBy(() -> servidorTemporarioService.create(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Este servidor é efetivo, não pode ser cadastrado como temporario.");
    }

    @Test
    void deveCriarServidorTemporario_ComDadosValidos() {
        // ARRANGE
        Long pessoaId = 1L;
        ServidorTemporarioRequest request = ServidorTemporarioRequest.builder()
                .pessoaId(pessoaId)
                .dataAdmissao(LocalDate.of(2024, 1, 1))
                .build();

        Pessoa pessoa = Pessoa.builder()
                .id(pessoaId)
                .dataNascimento(LocalDate.of(2000, 1, 1))
                .build();

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoa));
        when(servidorEfetivoRepository.existsByPessoaId(pessoaId)).thenReturn(false);
        when(servidorTemporarioRepository.existsByPessoaIdAndDataDemissaoIsNull(pessoaId)).thenReturn(false);

        // ACT
        servidorTemporarioService.create(request);

        // ASSERT
        verify(servidorTemporarioRepository, times(1)).save(any(ServidorTemporario.class));
    }

    @Test
    void deveAtualizarServidorTemporario_ComDadosValidos() {
        // ARRANGE
        Long pessoaId = 1L;
        ServidorTemporarioRequest request = ServidorTemporarioRequest.builder()
                .pessoaId(pessoaId)
                .dataAdmissao(LocalDate.of(2024, 1, 1))
                .dataDemissao(LocalDate.of(2025, 1, 1))
                .build();

        Pessoa pessoa = Pessoa.builder()
                .id(pessoaId)
                .dataNascimento(LocalDate.of(2000, 1, 1))
                .build();

        ServidorTemporario servidorExistente = ServidorTemporario.builder()
                .id(10L)
                .pessoaId(pessoaId)
                .dataAdmissao(LocalDate.of(2023, 1, 1))
                .build();

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoa));
        when(servidorEfetivoRepository.existsByPessoaId(pessoaId)).thenReturn(false);
        when(servidorTemporarioRepository.findByPessoaIdAndDataDemissaoIsNull(pessoaId))
                .thenReturn(Optional.of(servidorExistente));

        // ACT
        servidorTemporarioService.update(pessoaId, request);

        // ASSERT
        verify(servidorTemporarioRepository, times(1)).save(any(ServidorTemporario.class));
    }

    @Test
    void deveLancarExcecao_QuandoAtualizarServidorTemporarioDePessoaInexistente() {
        // ARRANGE
        Long pessoaId = 99L;
        ServidorTemporarioRequest request = ServidorTemporarioRequest.builder()
                .pessoaId(pessoaId)
                .dataAdmissao(LocalDate.of(2024, 1, 1))
                .build();

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> servidorTemporarioService.update(pessoaId, request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Pessoa não encontrada.");
    }

    @Test
    void deveLancarExcecao_QuandoAtualizarServidorTemporarioParaServidorEfetivo() {
        // ARRANGE
        Long pessoaId = 1L;
        ServidorTemporarioRequest request = ServidorTemporarioRequest.builder()
                .pessoaId(pessoaId)
                .dataAdmissao(LocalDate.of(2024, 1, 1))
                .build();

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(new Pessoa()));
        when(servidorEfetivoRepository.existsByPessoaId(pessoaId)).thenReturn(true);

        // ACT & ASSERT
        assertThatThrownBy(() -> servidorTemporarioService.update(pessoaId, request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Este servidor é EFETIVO, não pode ser alterado como temporario.");
    }

    @Test
    void deveLancarExcecao_QuandoAtualizarServidorTemporarioNaoExistente() {
        // ARRANGE
        Long pessoaId = 1L;
        ServidorTemporarioRequest request = ServidorTemporarioRequest.builder()
                .pessoaId(pessoaId)
                .dataAdmissao(LocalDate.of(2024, 1, 1))
                .build();

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(new Pessoa()));
        when(servidorEfetivoRepository.existsByPessoaId(pessoaId)).thenReturn(false);
        when(servidorTemporarioRepository.findByPessoaIdAndDataDemissaoIsNull(pessoaId))
                .thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> servidorTemporarioService.update(pessoaId, request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Este servidor não tem registro de Servidor Temporario em vigencia!");
    }
    @Test
    void deveRetornarTodosServidoresTemporarios_QuandoNaoInformarNome() {
        // ARRANGE
        Pageable pageable = PageRequest.of(0, 10);
    
        Pessoa pessoa1 = Pessoa.builder()
                .id(1L)
                .nome("Carlos Silva")
                .mae("Maria Silva")
                .dataNascimento(LocalDate.of(1990, 5, 10))
                .enderecos(new HashSet<>())  // 🔹 Adiciona um Set vazio para evitar NullPointerException
                .build();
    
        Pessoa pessoa2 = Pessoa.builder()
                .id(2L)
                .nome("Ana Souza")
                .mae("Joana Souza")
                .dataNascimento(LocalDate.of(1995, 7, 15))
                .enderecos(new HashSet<>())  // 🔹 Adiciona um Set vazio para evitar NullPointerException
                .build();
    
        ServidorTemporario servidor1 = ServidorTemporario.builder()
                .id(1L)
                .pessoaId(1L)
                .pessoa(pessoa1)  // 🔹 Associa a pessoa ao servidor temporário
                .dataAdmissao(LocalDate.of(2022, 1, 1))
                .build();
    
        ServidorTemporario servidor2 = ServidorTemporario.builder()
                .id(2L)
                .pessoaId(2L)
                .pessoa(pessoa2)  // 🔹 Associa a pessoa ao servidor temporário
                .dataAdmissao(LocalDate.of(2023, 5, 15))
                .build();
    
        Page<ServidorTemporario> pageServidores = new PageImpl<>(List.of(servidor1, servidor2), pageable, 2);
    
        when(servidorTemporarioRepository.findAllByDataDemissaoIsNull(pageable)).thenReturn(pageServidores);
    
        // ACT
        Page<ServidorTemporarioDTO> resultado = servidorTemporarioService.findAllServidoresTemporarios(null, pageable);
    
        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getContent()).hasSize(2);
    }    
    @Test
    void deveFiltrarServidoresTemporarios_PeloNome() {
        // ARRANGE
        Pageable pageable = PageRequest.of(0, 10);
        Pessoa pessoa1 = Pessoa.builder()
                .id(1L)
                .nome("Carlos Silva")
                .mae("Maria Silva")
                .dataNascimento(LocalDate.of(1990, 5, 10))
                .enderecos(new HashSet<>())  // 🔹 Adiciona um Set vazio para evitar NullPointerException
                .build();
    
        Pessoa pessoa2 = Pessoa.builder()
                .id(2L)
                .nome("Ana Souza")
                .mae("Joana Souza")
                .dataNascimento(LocalDate.of(1995, 7, 15))
                .enderecos(new HashSet<>())  // 🔹 Adiciona um Set vazio para evitar NullPointerException
                .build();
    
        ServidorTemporario servidor1 = ServidorTemporario.builder()
                .id(1L)
                .pessoaId(1L)
                .pessoa(pessoa1)  // 🔹 Associa a pessoa ao servidor temporário
                .dataAdmissao(LocalDate.of(2022, 1, 1))
                .build();
    
        ServidorTemporario servidor2 = ServidorTemporario.builder()
                .id(2L)
                .pessoaId(2L)
                .pessoa(pessoa2)  // 🔹 Associa a pessoa ao servidor temporário
                .dataAdmissao(LocalDate.of(2023, 5, 15))
                .build();

        String nomeBusca = "Carlos";
        // List<ServidorTemporario> servidores = List.of(
        //         ServidorTemporario.builder().id(1L).dataAdmissao(LocalDate.of(2022, 1, 1)).build()
        // );

//        Page<ServidorTemporario> pageServidores = new PageImpl<>(servidores, pageable, servidores.size());
        Page<ServidorTemporario> pageServidores = new PageImpl<>(List.of(servidor1, servidor2), pageable, 2);

        when(servidorTemporarioRepository.findAllByPessoaNomeContainingIgnoreCaseAndDataDemissaoIsNull(nomeBusca, pageable))
                .thenReturn(pageServidores);

        // ACT
        Page<ServidorTemporarioDTO> resultado = servidorTemporarioService.findAllServidoresTemporarios(nomeBusca, pageable);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getContent()).hasSize(2);
    }


}
