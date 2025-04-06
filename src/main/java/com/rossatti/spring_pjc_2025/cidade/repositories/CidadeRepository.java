package com.rossatti.spring_pjc_2025.cidade.repositories;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.rossatti.spring_pjc_2025.cidade.entities.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade,Long> {
        
    boolean existsByNomeAndUf(String name,String uf);
    
    Optional<Cidade> findByNomeAndUf(String nome, String uf);

    Page<Cidade> findByNomeContaining(String nome, Pageable pageable);

}
