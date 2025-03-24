package com.rossatti.spring_pjc_2025.pessoa_foto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rossatti.spring_pjc_2025.pessoa_foto.models.PessoaFoto;

@Repository
public interface PessoaFotoRepository extends JpaRepository<PessoaFoto,Long> {
    List<PessoaFoto> findByPessoaIdOrderByDataDesc(Long pessoaId);    
}


