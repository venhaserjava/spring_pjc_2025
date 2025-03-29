package com.rossatti.spring_pjc_2025.cidade.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rossatti.spring_pjc_2025.cidade.entities.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade,Long> {
    
    Page<Cidade> findByNomeContaining(String nome, Pageable pageable);

    boolean existsByNomeAndUf(String name,String uf);

    Optional<Cidade> findByNomeAndUf(String nome, String uf);

}
