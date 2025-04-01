package com.rossatti.spring_pjc_2025.unidade.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rossatti.spring_pjc_2025.unidade.entities.Unidade;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade,Long> {
    Page<Unidade> findByNomeContaining(String nome,Pageable pageable);
    boolean existsByNomeAndSigla(String nome, String sigla);
}
