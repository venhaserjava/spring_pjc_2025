package com.rossatti.spring_pjc_2025.servidor.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rossatti.spring_pjc_2025.cidade.entities.Cidade;
import com.rossatti.spring_pjc_2025.cidade.repositories.CidadeRepository;
import com.rossatti.spring_pjc_2025.endereco.entities.Endereco;
import com.rossatti.spring_pjc_2025.endereco.repositories.EnderecoRepository;
import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa.exceptions.PessoaNotFoundException;
import com.rossatti.spring_pjc_2025.pessoa.repositories.PessoaRepository;
import com.rossatti.spring_pjc_2025.servidor.dtos.request.ServidorRequest;
import com.rossatti.spring_pjc_2025.servidor.dtos.response.ServidorResponse;
import com.rossatti.spring_pjc_2025.servidor.mappers.ServidorMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServidorServiceImpl implements ServidorService {


    private final PessoaRepository pessoaRepository;
//    private final PessoaFotoService pessoaFotoService;
    private final CidadeRepository cidadeRepository;
    private final EnderecoRepository enderecoRepository;
    private final ServidorMapper servidorMapper;
    
    @Override
    public Page<ServidorResponse> findAll(String nome, Pageable pageable) {
        return pessoaRepository.findByNomeContaining(nome, pageable)
            .map(servidorMapper::toResponse);
    }

    @Override
    public ServidorResponse findById(Long id) {
        if(!pessoaRepository.existsById(id)){
            return new ServidorResponse();
        }
        return pessoaRepository.findById(id)
            .map(servidorMapper::toResponse)
            .orElseThrow(PessoaNotFoundException::new);
    }
    
    @Override
    public ServidorResponse update(Long id, ServidorRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    
    
    
    //@SuppressWarnings("unchecked")
    @Override
    @Transactional
    public ServidorResponse create(ServidorRequest request) {

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
                new Endereco(null,  request.getEnderecos().iterator().next().getTipoLogradouro(),
                                    request.getEnderecos().iterator().next().getLogradouro(), 
                                    request.getEnderecos().iterator().next().getNumero(),
                                    request.getEnderecos().iterator().next().getBairro(), 
                                    cidadeData, 
                                    null, 
                                    null
                     )
            );                
        }
        else {
            BeanUtils.copyProperties(endereco.get(), enderecoData);
        }
        //--------------------------------------------
        // Tratando dados de Pessoa (Servidor)
        //--------------------------------------------
        Pessoa pessoaData = new Pessoa();
        Optional<Pessoa> pessoa = pessoaRepository.findByNomeAndMaeAndDataNascimento(
                request.getNome(), request.getMae(), request.getDataNascimento()
        );
        if (pessoa.isEmpty()) {
//            pessoaData = pessoaRepository
            Pessoa pessoaToCreate = new Pessoa(null,   request.getNome(), 
                                                request.getMae(), 
                                                request.getPai(), 
                                                request.getSexo(), 
                                                request.getDataNascimento(), 
                                                null, 
                                                null);
            pessoaData =  pessoaRepository.save(pessoaToCreate);
            if (pessoaData.getEnderecos() == null) {
                pessoaData.setEnderecos(new HashSet<>());
            }
            pessoaData.getEnderecos().add(enderecoData);

//            pessoaData.getEnderecos().add(enderecoData);
            pessoaRepository.save(pessoaData);    
        } else {
            BeanUtils.copyProperties(pessoa.get(), pessoaData);
        }       
        

        return servidorMapper.toResponse(pessoaData);
    }

    public boolean existsServidor(String nome,String mae, LocalDate dataNascimento ){
        return pessoaRepository.existsByNomeAndMaeAndDataNascimento( nome, mae, dataNascimento );        
    }

}
