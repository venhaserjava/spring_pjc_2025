package com.rossatti.spring_pjc_2025.servidor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDate;
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


@ExtendWith(MockitoExtension.class)
class ServidorServiceImplTest {

    @InjectMocks
    private ServidorServiceImpl servidorService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private CidadeRepository cidadeRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private ServidorMapper servidorMapper;

    private Pessoa pessoa;
    private Cidade cidade;
    private Endereco endereco;
    private ServidorRequest servidorRequest;
    private ServidorResponse servidorResponse;

    @BeforeEach
    void setUp() {

//        cidade = new Cidade(1L, "São Paulo", "SP", null);
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

        pessoa = Pessoa.builder()
                .id(1L)
                .nome("João da Silva")
                .mae("Maria da Silva")
                .pai("José da Silva")
                .sexo("MASCULINO")
                .dataNascimento(LocalDate.of(1990, 5, 20))
                .enderecos(new HashSet<>(Set.of(endereco)))                  
                .build();
        pessoa.getEnderecos().add(endereco);

        servidorRequest = ServidorRequest.builder()
            .nome("João da Silva")
            .mae("Maria Silva")
            .pai("José Silva")
            .sexo("MASCULINO")
            .dataNascimento(LocalDate.of(1990, 5, 20))
            .enderecos(Set.of(
                ServidorEnderecoRequest.builder()
                    .tipoLogradouro("Rua")
                    .logradouro("Av. Paulista")
                    .numero(100)
                    .bairro("Centro")
                    .cidade(new ServidorCidadeRequest("São Paulo", "SP"))
                    .build()
            ))
            .build();
        // servidorResponse = new ServidorResponse(1L, "João da Silva", "Maria Silva", "José Silva",
        //                                         "MASCULINO", LocalDate.of(1990, 5, 20), null,
        //                                         "Rua", "Av. Paulista", 100, "Centro", "São Paulo", null);
        servidorResponse = ServidorResponse.builder()
                                .nome("João da Silva") 
                                .mae("Maria Silva")
                                .pai("José Silva")
                                .sexo("MASCULINO")
                                .dataNascimento(LocalDate.of(1990, 5, 20))
                                .tipoLogradouro("Rua")
                                .logradouro("Av. Paulista")
                                .numero(100)
                                .bairro("Centro")
                                .cidadeNome("São Paulo")                                                               
                                .build();


    }

    @Test
    void deveCriarServidorComSucesso() {
        // Simular busca por cidade e endereço
        when(cidadeRepository.findByNomeAndUf("São Paulo", "SP")).thenReturn(Optional.of(cidade));
        when(enderecoRepository.findByTipoLogradouroAndLogradouroAndNumeroAndBairroAndCidadeId(
            "Rua", "Av. Paulista", 100, "Centro", cidade.getId())).thenReturn(Optional.of(endereco));

        // Simular busca por pessoa inexistente (criação de novo registro)
        when(pessoaRepository.findByNomeAndMaeAndDataNascimento(
            "João da Silva", "Maria Silva", LocalDate.of(1990, 5, 20)))
            .thenReturn(Optional.empty());

        // Simular salvamento da pessoa
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        // Simular mapeamento para ServidorResponse
        when(servidorMapper.toResponse(any(Pessoa.class))).thenReturn(servidorResponse);

        // Chamar o método
        ServidorResponse response = servidorService.create(servidorRequest);

        // Verificar se o resultado está correto
        assertThat(response).isNotNull();
        assertThat(response.getNome()).isEqualTo("João da Silva");
        assertThat(response.getCidadeNome()).isEqualTo("São Paulo");

        // Verificar se os métodos corretos foram chamados
        //verify(pessoaRepository, times(1)).save(any(Pessoa.class));
        verify(pessoaRepository, times(2)).save(any(Pessoa.class));
        verify(cidadeRepository, times(1)).findByNomeAndUf("São Paulo", "SP");
        verify(enderecoRepository, times(1)).findByTipoLogradouroAndLogradouroAndNumeroAndBairroAndCidadeId(
            "Rua", "Av. Paulista", 100, "Centro", cidade.getId());
    }

    @Test
    void deveBuscarServidorPorId() {
        when(pessoaRepository.existsById(1L)).thenReturn(true);
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(servidorMapper.toResponse(any(Pessoa.class))).thenReturn(servidorResponse);

        ServidorResponse response = servidorService.findById(1L);

        assertThat(response).isNotNull();
        assertThat(response.getNome()).isEqualTo("João da Silva");

        verify(pessoaRepository, times(1)).findById(1L);
    }

    @Test
    void deveBuscarTodosOsServidores() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Pessoa> page = new PageImpl<>(List.of(pessoa));

        when(pessoaRepository.findByNomeContaining("João", pageable)).thenReturn(page);
        when(servidorMapper.toResponse(any(Pessoa.class))).thenReturn(servidorResponse);

        Page<ServidorResponse> result = servidorService.findAll("João", pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getNome()).isEqualTo("João da Silva");

        verify(pessoaRepository, times(1)).findByNomeContaining("João", pageable);
    }

    @Test
    void deveVerificarExistenciaDeServidor() {
        when(pessoaRepository.existsByNomeAndMaeAndDataNascimento(
            "João da Silva", "Maria Silva", LocalDate.of(1990, 5, 20)))
            .thenReturn(true);

        boolean exists = servidorService.existsServidor("João da Silva", "Maria Silva", LocalDate.of(1990, 5, 20));

        assertThat(exists).isTrue();
        verify(pessoaRepository, times(1)).existsByNomeAndMaeAndDataNascimento(
            "João da Silva", "Maria Silva", LocalDate.of(1990, 5, 20));
    }


}

/*

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
*/