package com.rossatti.spring_pjc_2025.pessoa_foto.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;
import com.rossatti.spring_pjc_2025.pessoa.exceptions.PessoaNotFoundException;
import com.rossatti.spring_pjc_2025.pessoa.repositories.PessoaRepository;
import com.rossatti.spring_pjc_2025.pessoa_foto.dtos.request.PessoaFotoRequest;
import com.rossatti.spring_pjc_2025.pessoa_foto.dtos.response.PessoaFotoResponse;
import com.rossatti.spring_pjc_2025.pessoa_foto.entities.PessoaFoto;
import com.rossatti.spring_pjc_2025.pessoa_foto.exceptions.PessoaFotoNotFoundException;
import com.rossatti.spring_pjc_2025.pessoa_foto.mappers.PessoaFotoMapper;
import com.rossatti.spring_pjc_2025.pessoa_foto.repository.PessoaFotoRepository;

import jakarta.transaction.Transactional;

@Service
public class PessoaFotoServiceImpl implements PessoaFotoService {


    @Autowired
    private PessoaFotoRepository repository;
    
    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Autowired
    private PessoaFotoMapper mapper;

    @Override
    public PessoaFotoResponse create(PessoaFotoRequest request) {
        Pessoa pessoa = pessoaRepository.findById(request.getPessoaId())
               .orElseThrow(() -> new PessoaFotoNotFoundException("Pessoa não encontrada"));
        PessoaFoto PessoaFoto = repository.save(mapper.toModel(request, pessoa));
        return mapper.toResponse(PessoaFoto);    
    }
        

    @Override
    public List<String> listarFotosPorPessoa(Long pessoaId) {
          return repository.findByPessoaIdOrderByDataDesc(pessoaId)
                .stream()
                .map(PessoaFoto::getHash)
                .collect(Collectors.toList());
    }
    // public Page<PessoaFotoResponse> findFotoByPerson(Long pessoaId, Pageable pageable) {
    //     return repository.findByPessoaIdOrderByDataDesc(pessoaId,pageable)
    //                      .map(mapper::toResponse);            
    // }


    @Override
    @Transactional
    public void saveFoto(Long pessoaId, String hash) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new PessoaNotFoundException("Pessoa não encontrada"));

        PessoaFoto foto = new PessoaFoto();
        foto.setPessoa(pessoa);
        foto.setData(LocalDate.now());        
        foto.setBucket("fotos");
        foto.setHash(hash);

        repository.save(foto);
    }

    @Override
    public String generateHash(MultipartFile file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(file.getBytes());
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes);
    }


    

}
