package com.techmant.Tecnico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techmant.Tecnico.model.Tecnico;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico,Long>{

}
