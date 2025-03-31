package com.rossatti.spring_pjc_2025.unidade.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rossatti.spring_pjc_2025.unidade.entities.UnidadeEndereco;

@Repository
public interface UnidadeEnderecoRepository extends JpaRepository<UnidadeEndereco,Long> {
}
