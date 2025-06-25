package com.techmant.Modelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techmant.Modelo.model.Modelo;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo,Long> {

}
