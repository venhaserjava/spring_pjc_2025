package com.rossatti.spring_pjc_2025.cidade.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rossatti.spring_pjc_2025.cidade.models.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade,Long> {
    
    Page<Cidade> findByNomeContaining(String nome, Pageable pageable);

    boolean existsByNomeAndUf(String name,String uf);

}
