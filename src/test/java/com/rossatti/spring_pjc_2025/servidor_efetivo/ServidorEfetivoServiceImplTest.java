package com.rossatti.spring_pjc_2025.servidor_efetivo;

import org.mockito.Mock;
import java.util.Optional;
import java.time.LocalDate;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import com.rossatti.spring_pjc_2025.lotacao.entities.Lotacao;
import com.rossatti.spring_pjc_2025.lotacao.repositories.LotacaoRepository;
import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa.repositories.PessoaRepository;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.dtos.request.ServidorEfetivoRequestDTO;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.dtos.response.ServidorEfetivoResponseDTO;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.entities.ServidorEfetivo;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.repositories.ServidorEfetivoRepository;
import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.services.ServidorEfetivoServiceImpl;
import com.rossatti.spring_pjc_2025.unidade.entities.Unidade;
import com.rossatti.spring_pjc_2025.unidade.repositories.UnidadeRepository;

@ExtendWith(MockitoExtension.class)
class ServidorEfetivoServiceImplTest {

    @Mock
    private ServidorEfetivoRepository servidorEfetivoRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private UnidadeRepository unidadeRepository;

    @Mock
    private LotacaoRepository lotacaoRepository;

    @InjectMocks
    private ServidorEfetivoServiceImpl servidorEfetivoService;

    private Pessoa pessoa;
    private Unidade unidade;
    private ServidorEfetivoRequestDTO requestDTO;
    private ServidorEfetivo servidorEfetivo;
    private Lotacao lotacao;

    @BeforeEach
    void setUp() {
        pessoa = Pessoa.builder()
                .id(1L)
                .nome("João Silva")
                .mae("Maria Silva")
                .sexo("M")
                .dataNascimento(LocalDate.of(1990, 5, 15))
                .build();

        unidade = Unidade.builder()
                .id(10L)
                .nome("Unidade Central")
                .sigla("UC")
                .build();

        requestDTO = ServidorEfetivoRequestDTO.builder()
                .pessoaId(1L)
                .matricula("12345")
                .unidadeId(10L)
                .dataLotacao(LocalDate.now())
                .portaria("PORT-2025")
                .build();

        servidorEfetivo = ServidorEfetivo.builder()
                .id(1L)
                .matricula("12345")
                .pessoa(pessoa)
                .build();

        lotacao = Lotacao.builder()
                .pessoa(pessoa)
                .unidade(unidade)
                .dataLotacao(LocalDate.now())
                .portaria("PORT-2025")
                .build();
    }

    @Test
    void criarServidorEfetivo_ComSucesso() {
        when(servidorEfetivoRepository.existsByPessoaId(requestDTO.getPessoaId())).thenReturn(false);
        when(pessoaRepository.findById(requestDTO.getPessoaId())).thenReturn(Optional.of(pessoa));
        when(unidadeRepository.findById(requestDTO.getUnidadeId())).thenReturn(Optional.of(unidade));
        when(servidorEfetivoRepository.save(any(ServidorEfetivo.class))).thenReturn(servidorEfetivo);
        when(lotacaoRepository.save(any(Lotacao.class))).thenReturn(lotacao);

        ServidorEfetivoResponseDTO response = servidorEfetivoService.criarServidorEfetivo(requestDTO);

        assertNotNull(response);
        assertEquals("João Silva", response.getNome());
        assertEquals("12345", response.getMatricula());
        assertEquals("Unidade Central", response.getUnidadeNome());
    }

    @Test
    void criarServidorEfetivo_DeveFalhar_QuandoPessoaJaCadastrada() {
        when(servidorEfetivoRepository.existsByPessoaId(requestDTO.getPessoaId())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> servidorEfetivoService.criarServidorEfetivo(requestDTO));

        assertEquals("Servidor efetivo já cadastrado!", exception.getMessage());
    }

    @Test
    void criarServidorEfetivo_DeveFalhar_QuandoPessoaNaoEncontrada() {
        when(servidorEfetivoRepository.existsByPessoaId(requestDTO.getPessoaId())).thenReturn(false);
        when(pessoaRepository.findById(requestDTO.getPessoaId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> servidorEfetivoService.criarServidorEfetivo(requestDTO));

        assertEquals("Pessoa não encontrada para o ID: 1", exception.getMessage());
    }

    @Test
    void criarServidorEfetivo_DeveFalhar_QuandoUnidadeNaoEncontrada() {
        when(servidorEfetivoRepository.existsByPessoaId(requestDTO.getPessoaId())).thenReturn(false);
        when(pessoaRepository.findById(requestDTO.getPessoaId())).thenReturn(Optional.of(pessoa));
        when(unidadeRepository.findById(requestDTO.getUnidadeId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> servidorEfetivoService.criarServidorEfetivo(requestDTO));

        assertEquals("Unidade não encontrada para o ID: 10", exception.getMessage());
    }
}
