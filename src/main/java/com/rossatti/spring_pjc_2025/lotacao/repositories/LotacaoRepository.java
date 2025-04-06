package com.rossatti.spring_pjc_2025.lotacao.repositories;

import java.util.List;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rossatti.spring_pjc_2025.lotacao.entities.Lotacao;

public interface LotacaoRepository  extends JpaRepository<Lotacao,Long>{

     List<Lotacao> findByDataLotacaoAfter(LocalDate data);

     Page<Lotacao> findByUnidadeId(Long unidId,Pageable pageable);     

     Long countByUnidadeId(Long unidadeId)  ;

     @Query("""
          SELECT l FROM Lotacao l
          JOIN l.pessoa p
          JOIN l.unidade u
          JOIN u.unidadeEndereco ue
          JOIN ue.endereco e
          WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))
          """)
     Page<Lotacao> findByPessoaNomeContaining(@Param("nome") String nome, Pageable pageable);          
}

