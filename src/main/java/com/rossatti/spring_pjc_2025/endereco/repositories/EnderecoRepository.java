package com.rossatti.spring_pjc_2025.endereco.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rossatti.spring_pjc_2025.endereco.models.Endereco;
@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
