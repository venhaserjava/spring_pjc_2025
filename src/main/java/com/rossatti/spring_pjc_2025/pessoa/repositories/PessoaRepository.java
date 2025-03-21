package com.rossatti.spring_pjc_2025.pessoa.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rossatti.spring_pjc_2025.pessoa.models.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa,Long> {
    Page<Pessoa> findByNomeContaining(String nome,Pageable pageable);
}
