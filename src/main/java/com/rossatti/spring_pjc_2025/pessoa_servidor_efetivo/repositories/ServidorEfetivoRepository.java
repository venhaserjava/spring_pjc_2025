package com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rossatti.spring_pjc_2025.pessoa_servidor_efetivo.entities.ServidorEfetivo;
@Repository
public interface ServidorEfetivoRepository extends JpaRepository<ServidorEfetivo,Long>{

    boolean existsByPessoaId(Long pessoaId);

    Optional<ServidorEfetivo> findByPessoaId(Long pessoaId);    

    List<ServidorEfetivo> findByIdIn(List<Long> pessoaIds);

    List<ServidorEfetivo> findByPessoaIdIn(List<Long> pessoaIds);
    
}
