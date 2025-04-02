package com.rossatti.spring_pjc_2025.Unidade.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.rossatti.spring_pjc_2025.cidade.dtos.request.CidadeRequest;
import com.rossatti.spring_pjc_2025.cidade.entities.Cidade;
import com.rossatti.spring_pjc_2025.cidade.repositories.CidadeRepository;
import com.rossatti.spring_pjc_2025.endereco.dtos.request.EnderecoRequest;
import com.rossatti.spring_pjc_2025.endereco.entities.Endereco;
import com.rossatti.spring_pjc_2025.endereco.repositories.EnderecoRepository;
import com.rossatti.spring_pjc_2025.servidor.dtos.request.ServidorCidadeRequest;
import com.rossatti.spring_pjc_2025.servidor.dtos.request.ServidorEnderecoRequest;
import com.rossatti.spring_pjc_2025.unidade.dtos.request.UnidadeRequest;
import com.rossatti.spring_pjc_2025.unidade.dtos.response.UnidadeResponse;
import com.rossatti.spring_pjc_2025.unidade.entities.Unidade;
import com.rossatti.spring_pjc_2025.unidade.mappers.UnidadeMapper;
import com.rossatti.spring_pjc_2025.unidade.repositories.UnidadeEnderecoRepository;
import com.rossatti.spring_pjc_2025.unidade.repositories.UnidadeRepository;
import com.rossatti.spring_pjc_2025.unidade.services.UnidadeServiceImpl;

@ExtendWith(MockitoExtension.class)
class UnidadeServiceTest {

    @Mock
    private UnidadeRepository unidadeRepository;
    
    @Mock
    private UnidadeMapper unidadeMapper;
    
    @Mock
    private CidadeRepository cidadeRepository;
    
    @Mock
    private EnderecoRepository enderecoRepository;
    
    @Mock
    private UnidadeEnderecoRepository unidadeEnderecoRepository;
    
    @InjectMocks
    private UnidadeServiceImpl unidadeService;
    
    private Unidade unidade;
    private UnidadeRequest unidadeRequest;
    private UnidadeResponse unidadeResponse;
    private CidadeRequest cidadeRequest;

    @BeforeEach
    void setUp() {

        EnderecoRequest enderecoRequest = EnderecoRequest.builder()
            .tipoLogradouro("Rua")
            .logradouro("Teste")
            .numero(123)
            .bairro("Centro")
            .cidadeId(7L)
            .build();
        Set<ServidorEnderecoRequest> enderecos = new HashSet<>();          
        
        unidade = Unidade.builder()
                .id(1L)
                .nome("Unidade Teste")
                .sigla("UT")
                .build();

        unidadeRequest = UnidadeRequest.builder()
                .nome("Unidade Teste")
                .sigla("UT")
                .enderecos(null)
                .enderecos(enderecos)
                .build();                
        
        unidadeResponse = UnidadeResponse.builder()
                .id(1L)
                .nome("Unidade Teste")
                .sigla("UT")
                .build();
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Unidade> page = new PageImpl<>(List.of(unidade));

        when(unidadeRepository.findByNomeContaining("Unidade", pageable)).thenReturn(page);
        when(unidadeMapper.toResponse(any(Unidade.class))).thenReturn(unidadeResponse);

        Page<UnidadeResponse> result = unidadeService.findAll("Unidade", pageable);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        assertEquals("Unidade Teste", result.getContent().get(0).getNome());
    }

    @Test
    void testFindUnitById_Success() {
        when(unidadeRepository.existsById(1L)).thenReturn(true);
        when(unidadeRepository.findById(1L)).thenReturn(Optional.of(unidade));
        when(unidadeMapper.toResponse(unidade)).thenReturn(unidadeResponse);

        UnidadeResponse result = unidadeService.findUnitById(1L);

        assertNotNull(result);
        assertEquals("Unidade Teste", result.getNome());
    }

    @Test
    void testFindUnitById_NotFound() {
        when(unidadeRepository.existsById(2L)).thenReturn(false);
        
        UnidadeResponse result = unidadeService.findUnitById(2L);
        
        assertNotNull(result);
        assertNull(result.getId());
    }

    @Test
    void testCreate() {

        // Criando um CidadeRequest válido
        ServidorCidadeRequest servidorCidadeRequest = ServidorCidadeRequest.builder()
             .nome("Cidade Teste")
             .uf("SP")
             .build();

        // Criando um ServidorEnderecoRequest válido
        ServidorEnderecoRequest servidorEnderecoRequest = ServidorEnderecoRequest.builder()
            .tipoLogradouro("Rua")
            .logradouro("Teste")
            .numero(123)
            .bairro("Centro")
            .cidade(servidorCidadeRequest)
            .build();        

            Set<ServidorEnderecoRequest> enderecos = new HashSet<>();
            enderecos.add(servidorEnderecoRequest);

        // Criando uma UnidadeRequest válida
        UnidadeRequest unidadeRequest = UnidadeRequest.builder()
        .nome("Unidade Teste")
        .sigla("UT")
        .enderecos(enderecos)
        .build();

        
        // Mock do repositório para garantir que a unidade não existe ainda
        when(unidadeRepository.existsByNomeAndSigla(anyString(), anyString())).thenReturn(false);
        when(cidadeRepository.findByNomeAndUf(anyString(), anyString())).thenReturn(Optional.of(new Cidade(1L, "Cidade Teste", "SP", null)));
        when(enderecoRepository.findByTipoLogradouroAndLogradouroAndNumeroAndBairroAndCidadeId(
            anyString(), anyString(), anyInt(), anyString(), anyLong()
        )).thenReturn(Optional.of(new Endereco(1L, "Rua", "Teste", 123, "Centro", new Cidade(1L, "Cidade Teste", "SP", null), new HashSet<>())));
        when(unidadeRepository.save(any(Unidade.class))).thenReturn(unidade);
        when(unidadeMapper.toResponse(any(Unidade.class))).thenReturn(unidadeResponse);
        
        UnidadeResponse result = unidadeService.create(unidadeRequest);
        
        assertNotNull(result);
        assertEquals("Unidade Teste", result.getNome());
    }

    @Test
    void testUpdate() {
        when(unidadeRepository.findById(1L)).thenReturn(Optional.of(unidade));
        when(unidadeRepository.save(any(Unidade.class))).thenReturn(unidade);
        when(unidadeMapper.toResponse(any(Unidade.class))).thenReturn(unidadeResponse);
        
        UnidadeResponse result = unidadeService.update(1L, unidadeRequest);
        
        assertNotNull(result);
        assertEquals("Unidade Teste", result.getNome());
    }    
}
