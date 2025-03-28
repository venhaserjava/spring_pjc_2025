package com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rossatti.spring_pjc_2025.pessoa_servidor_temporario.entities.ServidorTemporario;

@Repository
public interface ServidorTemporarioRepository  extends JpaRepository<ServidorTemporario,Long> {

}
