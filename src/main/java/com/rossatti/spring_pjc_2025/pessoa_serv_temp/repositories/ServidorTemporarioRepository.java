package com.rossatti.spring_pjc_2025.pessoa_serv_temp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rossatti.spring_pjc_2025.pessoa_serv_temp.models.ServidorTemporario;

@Repository
public interface ServidorTemporarioRepository  extends JpaRepository<ServidorTemporario,Long> {

}
