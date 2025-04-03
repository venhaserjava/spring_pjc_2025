package com.rossatti.spring_pjc_2025.servidor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.HashSet;
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
import org.springframework.data.domain.Sort;

import com.rossatti.spring_pjc_2025.cidade.entities.Cidade;
import com.rossatti.spring_pjc_2025.cidade.repositories.CidadeRepository;
import com.rossatti.spring_pjc_2025.endereco.entities.Endereco;
import com.rossatti.spring_pjc_2025.endereco.repositories.EnderecoRepository;
import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa.repositories.PessoaRepository;
import com.rossatti.spring_pjc_2025.servidor.dtos.request.ServidorCidadeRequest;
import com.rossatti.spring_pjc_2025.servidor.dtos.request.ServidorEnderecoRequest;
import com.rossatti.spring_pjc_2025.servidor.dtos.request.ServidorRequest;
import com.rossatti.spring_pjc_2025.servidor.dtos.response.ServidorResponse;
import com.rossatti.spring_pjc_2025.servidor.mappers.ServidorMapper;
import com.rossatti.spring_pjc_2025.servidor.services.ServidorServiceImpl;
//import com.rossatti.spring_pjc_2025.unidade.entities.Unidade;
import com.rossatti.spring_pjc_2025.unidade.entities.UnidadeEndereco;

@ExtendWith(MockitoExtension.class)
class ServidorServiceImplTest {

    @Mock
    private PessoaRepository pessoaRepository;
    
    @Mock
    private CidadeRepository cidadeRepository;
    
    @Mock
    private EnderecoRepository enderecoRepository;
    
    @Mock
    private ServidorMapper servidorMapper;

    @InjectMocks
    private ServidorServiceImpl servidorService;

    private ServidorRequest servidorRequest;
    private Pessoa pessoa;
    private Cidade cidade;
    private Endereco endereco;
    private ServidorResponse servidorResponse;
//  private Unidade unidade;    

    @BeforeEach
    void setUp() {
        // cidade = new Cidade(1L, "São Paulo", "SP", Endereco.builder()
        //                 .id(1L)
        //                 .tipoLogradouro("Rua")
        //                 .logradouro("Avenida Paulista")
        //                 .numero(1000)
        //                 .bairro("Centro")
        //                 .
        //                 .build());
        // unidade =  Unidade.builder()
        //                 .id(1L)
        //                 .nome("Almoxarifado")
        //                 .sigla("OF")
        //                 .build();

        cidade = Cidade.builder()
                    .id(1L)
                    .nome("São Paulo")
                    .uf("SP")
//                    .enderecos(Endereco.builder().build())
                    .build();
        Set<UnidadeEndereco> unidades = new HashSet<>();
                    
        endereco = Endereco.builder()
                .id(1L)
                .tipoLogradouro("Rua")
                .logradouro("Avenida Paulista")
                .numero(1000)
                .bairro("Centro")
                .cidade(cidade)
                .unidadeEnderecos(unidades)
                .build();
        pessoa = Pessoa.builder()
                    .id(1L)
                    .nome("João da Silva")
                    .mae("Maria da Silva")
                    .pai("José da Silva")
                    .sexo("MASCULINO")
                    .dataNascimento(LocalDate.of(1990, 5, 20))
                    .enderecos(new HashSet<>(Set.of(endereco)))                  
                    .build();

        // Corrigindo o valor da UF para "SP"
        ServidorCidadeRequest cidadeRequest = ServidorCidadeRequest.builder()
                        .nome("São Paulo")            
                        .uf("SP")
                        .build();

        Set<ServidorEnderecoRequest> enderecos = new HashSet<>();
        enderecos.add(ServidorEnderecoRequest.builder()
            .tipoLogradouro("Rua")                       
            .logradouro("Avenida Paulista")
            .numero(1000)
            .bairro("Centro")        
            .cidade(cidadeRequest)        
            .build());                                    
        
        servidorRequest = ServidorRequest.builder()
                .nome("João da Silva")
                .mae("Maria da Silva")
                .pai("José da Silva")
                .sexo("MASCULINO")
                .dataNascimento(LocalDate.of(1990, 5, 20))
                .enderecos(enderecos)
                .build();
        
        servidorResponse = new ServidorResponse();
//        servidorResponse = new ServidorResponse(1L, "João da Silva", "Maria da Silva", "José da Silva", "MASCULINO", LocalDate.of(1990, 5, 20), null, "Rua", "Avenida Paulista", 1000, "Centro", "São Paulo", null);        
    }    
    @Test
    void deveCriarServidorComSucesso() {
//        when(cidadeRepository.findByNomeAndUf("São Paulo", "SP")).thenReturn(Optional.of(cidade));
//        when(cidadeRepository.findByNomeAndUf(eq("São Paulo"), eq("SP"))).thenReturn(Optional.of(cidade));
        when(cidadeRepository.findByNomeAndUf(anyString(), anyString())).thenReturn(Optional.of(cidade));
        when(enderecoRepository.findByTipoLogradouroAndLogradouroAndNumeroAndBairroAndCidadeId(
                "Rua", "Avenida Paulista", 1000, "Centro", cidade.getId())).thenReturn(Optional.of(endereco));
        when(pessoaRepository.findByNomeAndMaeAndDataNascimento("João da Silva", "Maria da Silva", LocalDate.of(1990, 5, 20)))
                .thenReturn(Optional.empty());
        when(pessoaRepository.save(any())).thenReturn(pessoa);
        when(servidorMapper.toResponse(any())).thenReturn(servidorResponse);
        
        ServidorResponse response = servidorService.create(servidorRequest);
        
        assertNotNull(response);
        assertEquals("João da Silva", response.getNome());
        verify(pessoaRepository, times(2)).save(any());
    }

    @Test
    void deveFalharAoCriarServidorSePessoaJaExiste() {
        when(pessoaRepository.findByNomeAndMaeAndDataNascimento("João da Silva", "Maria da Silva", LocalDate.of(1990, 5, 20)))
                .thenReturn(Optional.of(pessoa));
        
        ServidorResponse response = servidorService.create(servidorRequest);
        
        assertNotNull(response);
        assertEquals("João da Silva", response.getNome());
        verify(pessoaRepository, never()).save(any());
    }

    @Test
    void deveBuscarServidorPorIdComSucesso() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(servidorMapper.toResponse(any())).thenReturn(servidorResponse);
        
        ServidorResponse response = servidorService.findById(1L);
        
        assertNotNull(response);
        assertEquals("João da Silva", response.getNome());
    }

    @Test
    void deveRetornarListaPaginadaDeServidores() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nome"));
        Page<Pessoa> page = new PageImpl<>(Set.of(pessoa).stream().toList(), pageable, 1);
        
        when(pessoaRepository.findByNomeContaining("João", pageable)).thenReturn(page);
        when(servidorMapper.toResponse(any())).thenReturn(servidorResponse);
        
        Page<ServidorResponse> responsePage = servidorService.findAll("João", pageable);
        
        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
    }
}
