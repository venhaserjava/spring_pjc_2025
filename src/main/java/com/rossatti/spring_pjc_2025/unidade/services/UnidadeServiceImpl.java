package com.rossatti.spring_pjc_2025.unidade.services;

import java.util.HashSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rossatti.spring_pjc_2025.cidade.entities.Cidade;
import com.rossatti.spring_pjc_2025.cidade.repositories.CidadeRepository;
import com.rossatti.spring_pjc_2025.endereco.entities.Endereco;
import com.rossatti.spring_pjc_2025.endereco.repositories.EnderecoRepository;
import com.rossatti.spring_pjc_2025.unidade.dtos.request.UnidadeRequest;
import com.rossatti.spring_pjc_2025.unidade.dtos.response.UnidadeResponse;
import com.rossatti.spring_pjc_2025.unidade.entities.Unidade;
import com.rossatti.spring_pjc_2025.unidade.entities.UnidadeEndereco;
import com.rossatti.spring_pjc_2025.unidade.exceptions.UnidadeNotFoundException;
import com.rossatti.spring_pjc_2025.unidade.mappers.UnidadeMapper;
import com.rossatti.spring_pjc_2025.unidade.repositories.UnidadeEnderecoRepository;
import com.rossatti.spring_pjc_2025.unidade.repositories.UnidadeRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnidadeServiceImpl implements UnidadeService {

    private final UnidadeRepository repository;
    private final UnidadeMapper mapper;
    private final CidadeRepository cidadeRepository;
    private final EnderecoRepository enderecoRepository;
    private final UnidadeEnderecoRepository unidadeEnderecoRepository;

    @Override
    public Page<UnidadeResponse> findAll(String nome,Pageable pageable) {
        return repository.findByNomeContaining(nome,pageable)            
            .map(mapper::toResponse);                        
    }

    @Override
    public UnidadeResponse findUnitById(Long id) {
        if(!repository.existsById(id)){
            return new UnidadeResponse();
        }
        return repository.findById(id)
            .map(mapper::toResponse)
            .orElseThrow(UnidadeNotFoundException::new);        
    }

    @Override
    @Transactional
    public UnidadeResponse create(UnidadeRequest request) {
        if (request == null) {
            return null;
        }
        
        if (!repository.existsByNomeAndSigla(request.getNome(), request.getSigla())) { 
            //----------------------------------
            /// Tratando dados de Cidade
            //---------------------------------- 
            Cidade cidadeData = cidadeRepository
                .findByNomeAndUf(request.getEnderecos().iterator().next().getCidade().getNome(), 
                                 request.getEnderecos().iterator().next().getCidade().getUf())
                .orElseGet(() -> cidadeRepository.save(new Cidade(null, 
                                                                  request.getEnderecos().iterator().next().getCidade().getNome(), 
                                                                  request.getEnderecos().iterator().next().getCidade().getUf(), 
                                                                  null)));
    
            //--------------------------------------------
            // Tratando dados de Endereco
            //--------------------------------------------
            Endereco enderecoData = enderecoRepository
                .findByTipoLogradouroAndLogradouroAndNumeroAndBairroAndCidadeId(
                    request.getEnderecos().iterator().next().getTipoLogradouro(),
                    request.getEnderecos().iterator().next().getLogradouro(),
                    request.getEnderecos().iterator().next().getNumero(),
                    request.getEnderecos().iterator().next().getBairro(),
                    cidadeData.getId()
                ).orElseGet(() -> enderecoRepository.save(Endereco.builder()
                    .tipoLogradouro(request.getEnderecos().iterator().next().getTipoLogradouro())
                    .logradouro(request.getEnderecos().iterator().next().getLogradouro())
                    .numero(request.getEnderecos().iterator().next().getNumero())
                    .bairro(request.getEnderecos().iterator().next().getBairro())
                    .cidade(cidadeData)
                    .unidadeEnderecos(new HashSet<>()) 
                    .build()));
    
            // Criando a unidade sem associação direta ao endereço
            var unidadeTocreate = repository.save(new Unidade(
                null,
                request.getNome(),
                request.getSigla(),
                null, // Lista de lotações inicializada como null
                null // Relação com UnidadeEndereco ainda não definida
            ));
    
            // Criando a relação intermediária UnidadeEndereco
            var unidadeEndereco = new UnidadeEndereco(null, unidadeTocreate, enderecoData);
    
            // Salvando a relação intermediária após a unidade já estar salva
            unidadeEndereco = unidadeEnderecoRepository.save(unidadeEndereco);
    
            // Associando a relação intermediária à unidade salva
            unidadeTocreate.setUnidadeEndereco(unidadeEndereco);
    
            return mapper.toResponse(unidadeTocreate);
        } 
    
        return new UnidadeResponse();
    }

    @Override
    @Transactional
    public UnidadeResponse update(Long id, UnidadeRequest request) {
        // Verifica se a unidade existe
        Unidade unidade = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada com ID: " + id));

        // Atualiza os campos permitidos
        unidade.setNome(request.getNome());
        unidade.setSigla(request.getSigla());

        // Salva e retorna a unidade atualizada
        Unidade unidadeAtualizada = repository.save(unidade);
        return mapper.toResponse(unidadeAtualizada);
    }

/*   
    @Override
    @Transactional

    public UnidadeResponse create(UnidadeRequest request) {
        if (request==null) {
            return null;            
        }
        if ( ! repository.existsByNomeAndSigla(request.getNome(), request.getSigla() ) ) 
        {                   
            //----------------------------------
            /// Tratando dados de Cidade
            //----------------------------------      
            Cidade cidadeData = new Cidade();
            Optional<Cidade> cidade = cidadeRepository.findByNomeAndUf(
                request.getEnderecos().iterator().next().getCidade().getNome(), 
                request.getEnderecos().iterator().next().getCidade().getUf()
            );
            if (cidade.isEmpty()) {
                cidadeData = cidadeRepository.save(
                    new Cidade(null, request.getEnderecos().iterator().next().getCidade().getNome(), 
                                        request.getEnderecos().iterator().next().getCidade().getUf(), 
                                        null
                            )
                );            
            }
            else {
                BeanUtils.copyProperties(cidade.get(), cidadeData);
            }
            //--------------------------------------------
            // Tratando dados de Endereco
            //--------------------------------------------
            Endereco enderecoData = new Endereco();
            Optional<Endereco> endereco = enderecoRepository.findByTipoLogradouroAndLogradouroAndNumeroAndBairroAndCidadeId(
                request.getEnderecos().iterator().next().getTipoLogradouro(),
                request.getEnderecos().iterator().next().getLogradouro(),
                request.getEnderecos().iterator().next().getNumero(),
                request.getEnderecos().iterator().next().getBairro(),
                cidadeData.getId()
            );
            if (endereco.isEmpty()) {
                enderecoData = enderecoRepository.save(
                    Endereco.builder()
                    .tipoLogradouro(request.getEnderecos().iterator().next().getTipoLogradouro())
                    .logradouro(request.getEnderecos().iterator().next().getLogradouro())
                    .numero(request.getEnderecos().iterator().next().getNumero())
                    .bairro(request.getEnderecos().iterator().next().getBairro())
                    .cidade(cidadeData)
                    .unidadeEnderecos(new HashSet<>())    
                    .build()
                    );
            }
            else {
                BeanUtils.copyProperties(endereco.get(), enderecoData);
            }
            // Criando a unidade sem associação direta ao endereço
            var unidadeTocreate = new Unidade(
                null,
                request.getNome(),
                request.getSigla(),
                null, // Lista de lotações inicializada como null
                null  // Relação com UnidadeEndereco ainda não definida
            );
            // Criando a relação intermediária UnidadeEndereco
            var unidadeEndereco = new UnidadeEndereco(null, unidadeTocreate, enderecoData);
            // Associando a relação intermediária à unidade
            unidadeTocreate.setUnidadeEndereco(unidadeEndereco);
            var unidadeCreated = repository.save(unidadeTocreate);
            return mapper.toResponse(unidadeCreated);
//            return new UnidadeResponse();
            
        } else {
            return new UnidadeResponse();
        }       
    }

    @Override
    public UnidadeResponse update(Long id, UnidadeRequest request) {
        if (id==null || request==null) {
            throw new IllegalArgumentException("Os parametros id ou request não pode serm nulos.");            
        }
        
        var unidadeToUpdate = repository.findById(id)
                                .orElseThrow(UnidadeNotFoundException::new);
        BeanUtils.copyProperties(request,unidadeToUpdate,"id");
        var unidadeUpdated = repository.save(unidadeToUpdate);
        return mapper.toResponse(unidadeUpdated);
    }

    @Override
    public boolean existUnit(String nome, String sigla) {
        return repository.existsByNomeAndSigla(nome, sigla);
    }
*/
}
