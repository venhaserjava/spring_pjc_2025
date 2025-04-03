package com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.entities.ServidorTemporario;

@Repository
public interface ServidorTemporarioRepository  extends JpaRepository<ServidorTemporario,Long> {
    boolean existsByPessoaIdAndDataDemissaoIsNull(Long pessoaId);
    Optional<ServidorTemporario> findByPessoaIdAndDataDemissaoIsNull(Long pessoaId);
    Page<ServidorTemporario> findAllByDataDemissaoIsNull(Pageable pageable);
    Page<ServidorTemporario> findAllByPessoaNomeContainingIgnoreCaseAndDataDemissaoIsNull(String nome, Pageable pageable);
}
