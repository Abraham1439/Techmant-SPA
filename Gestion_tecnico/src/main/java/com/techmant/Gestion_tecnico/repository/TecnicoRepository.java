package com.techmant.Gestion_tecnico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techmant.Gestion_tecnico.model.Tecnico;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico,Long>{

}
