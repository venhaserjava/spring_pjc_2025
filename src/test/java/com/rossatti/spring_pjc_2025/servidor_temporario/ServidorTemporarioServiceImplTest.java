package com.rossatti.spring_pjc_2025.servidor_temporario;

import org.mockito.Mock;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.time.LocalDate;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import com.rossatti.spring_pjc_2025.cidade.entities.Cidade;
import com.rossatti.spring_pjc_2025.endereco.entities.Endereco;
import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa.repositories.PessoaRepository;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.repositories.ServidorEfetivoRepository;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.dtos.request.ServidorTemporarioRequest;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.entities.ServidorTemporario;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.mappers.ServidorTemporarioMapper;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.repositories.ServidorTemporarioRepository;
import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.service.ServidorTemporarioServiceImpl;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ServidorTemporarioServiceImplTest {

    @InjectMocks
    private ServidorTemporarioServiceImpl servidorTemporarioService;

    @Mock
    private ServidorTemporarioRepository servidorTemporarioRepository;
    
    @Mock
    private ServidorEfetivoRepository servidorEfetivoRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private ServidorTemporarioMapper mapper;

    private ServidorTemporarioRequest request;
    private Pessoa pessoa;
    private ServidorTemporario servidorTemporario;

    private Cidade cidade;

    private Endereco endereco;

    @BeforeEach
    void setUp() {

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
        pessoa = Pessoa.builder()
                .id(1L)
                .nome("João Silva")
                .mae("Maria Silva")
                .pai("José Silva")
                .sexo("M")
                .dataNascimento(LocalDate.of(2000, 1, 1))
                .enderecos(new HashSet<>(Set.of(endereco)))                 
                .build();

        servidorTemporario = ServidorTemporario.builder()
                .id(1L)
                .pessoaId(pessoa.getId())
                .dataAdmissao(LocalDate.of(2022, 1, 1))
                .build();

        request = ServidorTemporarioRequest.builder()
                .pessoaId(pessoa.getId())
                .dataAdmissao(LocalDate.of(2022, 1, 1))
                .build();
    }

    @Test
    void deveCriarServidorTemporarioComSucesso() {
        // ARRANGE
        request.setPessoaId(1L);  // Garante que tem um ID válido
        System.out.println("Pessoa ID no request: " + request.getPessoaId());
    
        doReturn(Optional.of(pessoa)).when(pessoaRepository).findById(1L);
        doReturn(false).when(servidorEfetivoRepository).existsByPessoaId(anyLong());
        doReturn(false).when(servidorTemporarioRepository).existsByPessoaIdAndDataDemissaoIsNull(anyLong());
        doReturn(servidorTemporario).when(servidorTemporarioRepository).save(any());
    
        // ACT & ASSERT
        assertDoesNotThrow(() -> servidorTemporarioService.create(request));
    
        // Verifica se o repositório foi chamado corretamente
        verify(servidorTemporarioRepository, times(1)).save(any(ServidorTemporario.class));
    }    

    // Erro ao criar Servidor Temporário com Pessoa inexistente
    @Test
    void deveLancarErro_QuandoPessoaNaoExiste() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            servidorTemporarioService.create(request);
        });

        assertTrue(exception.getMessage().contains("Pessoa não encontrada"));
    }

    // Erro ao criar Servidor Temporário quando a pessoa já é Servidor Efetivo
    @Test
    void deveLancarErro_QuandoPessoaJaEServidorEfetivo() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));
        when(servidorEfetivoRepository.existsByPessoaId(anyLong())).thenReturn(true);

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            servidorTemporarioService.create(request);
        });

        assertTrue(exception.getMessage().contains("Este servidor é efetivo"));
    }

    // Erro ao criar Servidor Temporário se já houver um ativo
    @Test
    void deveLancarErro_QuandoJaExisteServidorTemporarioAtivo() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));
        when(servidorEfetivoRepository.existsByPessoaId(anyLong())).thenReturn(false);
        when(servidorTemporarioRepository.existsByPessoaIdAndDataDemissaoIsNull(anyLong())).thenReturn(true);

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            servidorTemporarioService.create(request);
        });

        assertTrue(exception.getMessage().contains("já está cadastrado como Temporário"));
    }

    // Erro ao criar Servidor Temporário com data de admissão inválida
    @Test
    void deveLancarErro_QuandoDataAdmissaoInvalida() {
        request.setDataAdmissao(LocalDate.of(2015, 1, 1)); // Antes dos 18 anos

        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));
        when(servidorEfetivoRepository.existsByPessoaId(anyLong())).thenReturn(false);
        when(servidorTemporarioRepository.existsByPessoaIdAndDataDemissaoIsNull(anyLong())).thenReturn(false);

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            servidorTemporarioService.create(request);
        });

        assertTrue(exception.getMessage().contains("A Data de ADMISSAO deve ser posterior ao 18º aniversário"));
    }

    // Erro ao criar Servidor Temporário com data de demissão antes da admissão
    @Test
    void deveLancarErro_QuandoDataDemissaoAntesDaAdmissao() {
        request.setDataDemissao(LocalDate.of(2021, 1, 1)); // Antes da admissão

        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));
        when(servidorEfetivoRepository.existsByPessoaId(anyLong())).thenReturn(false);
        when(servidorTemporarioRepository.existsByPessoaIdAndDataDemissaoIsNull(anyLong())).thenReturn(false);

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            servidorTemporarioService.create(request);
        });

        assertTrue(exception.getMessage().contains("A Data de DEMISSÃO deve ser posterior"));
    }
}
