package com.rossatti.spring_pjc_2025.pessoa.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rossatti.spring_pjc_2025.pessoa.entities.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa,Long> {
    Page<Pessoa> findByNomeContaining(String nome,Pageable pageable);
}
