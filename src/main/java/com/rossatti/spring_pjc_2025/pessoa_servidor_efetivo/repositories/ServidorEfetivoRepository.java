package com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.repositories;

//import java.util.Optional;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.entities.ServidorEfetivo;

public interface ServidorEfetivoRepository extends JpaRepository<ServidorEfetivo,Long>{
//    Page<ServidorEfetivo> findAll(String nome, Pageable pageable);
    boolean existsByPessoaId(Long pessoaId);
//    Optional<ServidorTemporario> findByPessoaIdAndDataDemissaoIsNull(Long pessoaId);

}
