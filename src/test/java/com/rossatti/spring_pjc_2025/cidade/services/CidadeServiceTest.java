package com.rossatti.spring_pjc_2025.cidade.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.rossatti.spring_pjc_2025.cidade.entities.Cidade;
import com.rossatti.spring_pjc_2025.cidade.repositories.CidadeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CidadeServiceTest {

    @Mock
    private CidadeRepository cidadeRepository;

    @InjectMocks
    private CidadeServiceImpl cidadeService;

    private Cidade cidade;

    @BeforeEach
    void setUp() {
        cidade = new Cidade(1L, "São Paulo", "SP", null);
    }

    @Test
    void deveCriarCidadeSeNaoExistir() {
        when(cidadeRepository.findByNomeAndUf("São Paulo", "SP")).thenReturn(Optional.empty());
        when(cidadeRepository.save(any(Cidade.class))).thenReturn(cidade);
        
        Cidade result = cidadeService.criarCidadeSeNaoExistir("São Paulo", "SP");
        
        assertNotNull(result);
        assertEquals("São Paulo", result.getNome());
        verify(cidadeRepository).save(any(Cidade.class));
    }

    @Test
    void naoDeveCriarCidadeSeJaExistir() {
        when(cidadeRepository.findByNomeAndUf("São Paulo", "SP")).thenReturn(Optional.of(cidade));
        
        Cidade result = cidadeService.criarCidadeSeNaoExistir("São Paulo", "SP");
        
        assertNotNull(result);
        assertEquals("São Paulo", result.getNome());
        verify(cidadeRepository, never()).save(any(Cidade.class));
    }
}
