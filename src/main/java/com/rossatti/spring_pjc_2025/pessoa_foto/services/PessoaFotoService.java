package com.rossatti.spring_pjc_2025.pessoa_foto.services;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import com.rossatti.spring_pjc_2025.pessoa_foto.dtos.request.PessoaFotoRequest;
import com.rossatti.spring_pjc_2025.pessoa_foto.dtos.response.PessoaFotoResponse;

public interface PessoaFotoService {
    PessoaFotoResponse create(PessoaFotoRequest request);    
//    Page<PessoaFotoResponse> findFotoByPerson(Long pessoaId,Pageable pageable);
     List<String> listarFotosPorPessoa(Long pessoaId);
    void saveFoto(Long pessoaId, String hash);
    String generateHash(MultipartFile file) throws Exception;
}
