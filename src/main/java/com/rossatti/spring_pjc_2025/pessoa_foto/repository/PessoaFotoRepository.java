package com.rossatti.spring_pjc_2025.pessoa_foto.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import com.rossatti.spring_pjc_2025.pessoa_foto.models.PessoaFoto;

public interface PessoaFotoRepository extends JpaRepository<PessoaFoto,Long> {
    Page<PessoaFoto> findByPessoaIdOrderByDataDesc(Long pessoaId);
}


