package com.rossatti.spring_pjc_2025.lotacao.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rossatti.spring_pjc_2025.lotacao.entities.Lotacao;

public interface LotacaoRepository  extends JpaRepository<Lotacao,Long>{
     List<Lotacao> findByDataLotacaoAfter(LocalDate data);
     Page<Lotacao> findByUnidadeId(Long unidId,Pageable pageable);     
     // @Query("SELECT l FROM Lotacao l WHERE l.unidade.id = :unidadeId ORDER BY l.unidade.nome")
     // Page<Lotacao> findByUnidadeId(@Param("unidadeId") Long unidadeId, Pageable pageable);         
     Long countByUnidadeId(Long unidadeId)     ;
     List<Lotacao> findByPessoaNomeContaining(String nome);
}
//xx     Page<Lotacao> findByNomeContaining(String nome,Pageable pageable);
