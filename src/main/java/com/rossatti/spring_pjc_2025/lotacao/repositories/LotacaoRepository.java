package com.rossatti.spring_pjc_2025.lotacao.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rossatti.spring_pjc_2025.lotacao.models.Lotacao;

public interface LotacaoRepository  extends JpaRepository<Lotacao,Long>{
     List<Lotacao> findByDataLotacaoAfter(LocalDate data);
}
