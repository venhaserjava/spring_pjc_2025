package com.rossatti.spring_pjc_2025.endereco.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rossatti.spring_pjc_2025.cidade.entities.Cidade;
import com.rossatti.spring_pjc_2025.endereco.entities.Endereco;
@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    Page<Endereco> findByLogradouroContaining(String logradouro, Pageable pageable);
    boolean existsByLogradouroAndNumeroAndBairroAndCidade(
        String logradouro,
        Integer numero,
        String bairro,
        Cidade cidade);
}
