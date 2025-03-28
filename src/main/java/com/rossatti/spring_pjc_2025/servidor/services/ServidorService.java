package com.rossatti.spring_pjc_2025.servidor.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.rossatti.spring_pjc_2025.servidor.dtos.request.ServidorRequest;
import com.rossatti.spring_pjc_2025.servidor.dtos.response.ServidorResponse;

public interface ServidorService {
    public Page<ServidorResponse> findAll(String nome,Pageable pageable);
    public ServidorResponse findById(Long id);
    public ServidorResponse create(ServidorRequest request);
    public ServidorResponse update(Long id,ServidorRequest request);
}
