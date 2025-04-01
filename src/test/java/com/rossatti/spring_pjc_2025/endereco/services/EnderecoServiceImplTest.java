package com.rossatti.spring_pjc_2025.endereco.services;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

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

import com.rossatti.spring_pjc_2025.cidade.entities.Cidade;
import com.rossatti.spring_pjc_2025.cidade.repositories.CidadeRepository;
import com.rossatti.spring_pjc_2025.endereco.dtos.request.EnderecoRequest;
import com.rossatti.spring_pjc_2025.endereco.dtos.response.EnderecoResponse;
import com.rossatti.spring_pjc_2025.endereco.entities.Endereco;
import com.rossatti.spring_pjc_2025.endereco.exceptions.EnderecoNotFoundException;
import com.rossatti.spring_pjc_2025.endereco.mappers.EnderecoMapperImpl;
import com.rossatti.spring_pjc_2025.endereco.repositories.EnderecoRepository;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceImplTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private CidadeRepository cidadeRepository;

    @Mock
    private EnderecoMapperImpl enderecoMapper;

    @InjectMocks
    private EnderecoServiceImpl enderecoService;

    private Endereco endereco;
    private EnderecoRequest enderecoRequest;
    private EnderecoResponse enderecoResponse;
    private Cidade cidade;

    @BeforeEach
    void setUp() {
//        cidade = new Cidade(1L, "São Paulo");
        cidade = Cidade.builder()
                    .id(1L)
                    .nome("São Paulo")
                    .uf("SP")
                    .build();
                    
//        endereco = new Endereco(1L, "Rua A", "123", cidade);
        endereco = Endereco.builder()
                    .id(1L)
                    .tipoLogradouro("Rua")
                    .logradouro("Rua A")
                    .numero(123)
                    .bairro("Vila Cruz Alta")
                    .cidade(cidade)
                    .build();
//        enderecoRequest = new EnderecoRequest("Rua A", "123", 1L);
        enderecoRequest = EnderecoRequest.builder()
                            .tipoLogradouro("Rua")
                            .logradouro("Rua A")
                            .numero(123)
                            .bairro("Vila Cruz Alta")
                            .cidadeId(1L)
                            .build();
//        enderecoResponse = new EnderecoResponse(1L, "Rua A", "123", "São Paulo");
        enderecoResponse = EnderecoResponse.builder()
                            .tipoLogradouro("Rua")
                            .logradouro("Rua A")
                            .numero(123)
                            .bairro("Vila Cruz Alta")
                            .cidadeNome("São Paulo - SP")
                            .build();
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        when(enderecoRepository.findByLogradouroContaining("Rua A", pageable))
                .thenReturn(new PageImpl<>(List.of(endereco)));
        when(enderecoMapper.toResponse(any(Endereco.class))).thenReturn(enderecoResponse);

        Page<EnderecoResponse> result = enderecoService.findAll("Rua A", pageable);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        assertEquals("Rua A", result.getContent().get(0).getLogradouro());
    }

    @Test
    void testFindById_ExistingEndereco() {
        when(enderecoRepository.existsById(1L)).thenReturn(true);
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));
        when(enderecoMapper.toResponse(endereco)).thenReturn(enderecoResponse);

        EnderecoResponse result = enderecoService.findById(1L);

        assertNotNull(result);
        assertEquals("Rua A", result.getLogradouro());
    }

    @Test
    void testFindById_NonExistingEndereco() {
        when(enderecoRepository.existsById(2L)).thenReturn(false);
        
        EnderecoResponse result = enderecoService.findById(2L);

        assertNotNull(result);
        assertNull(result.getLogradouro());
    }

    @Test
    void testCreate() {
        when(cidadeRepository.findById(1L)).thenReturn(Optional.of(cidade));
        when(enderecoMapper.toModel(enderecoRequest, cidade)).thenReturn(endereco);
        when(enderecoRepository.save(endereco)).thenReturn(endereco);
        when(enderecoMapper.toResponse(endereco)).thenReturn(enderecoResponse);

        EnderecoResponse result = enderecoService.create(enderecoRequest);

        assertNotNull(result);
        assertEquals("Rua A", result.getLogradouro());
    }

    @Test
    void testUpdate() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));
        when(cidadeRepository.findById(1L)).thenReturn(Optional.of(cidade));
        when(enderecoMapper.toModel(enderecoRequest, cidade)).thenReturn(endereco);
        when(enderecoRepository.save(endereco)).thenReturn(endereco);
        when(enderecoMapper.toResponse(endereco)).thenReturn(enderecoResponse);

        EnderecoResponse result = enderecoService.update(1L, enderecoRequest);

        assertNotNull(result);
        assertEquals("Rua A", result.getLogradouro());
    }

    @Test
    void testUpdate_NonExistingEndereco() {
        when(enderecoRepository.findById(2L)).thenReturn(Optional.empty());        
        assertThrows(EnderecoNotFoundException.class, () -> enderecoService.update(2L, enderecoRequest));
    }
}
