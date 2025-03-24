package com.rossatti.spring_pjc_2025.pessoa_foto.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rossatti.spring_pjc_2025.pessoa_foto.models.PessoaFoto;

@Repository
public interface PessoaFotoRepository extends JpaRepository<PessoaFoto,Long> {
    Page<PessoaFoto> findByPessoaIdOrderByDataDesc(Long pessoaId,Pageable pageable);
}


