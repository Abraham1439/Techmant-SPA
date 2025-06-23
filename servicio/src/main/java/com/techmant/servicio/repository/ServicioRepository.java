package com.techmant.servicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techmant.servicio.model.Servicio;

@Repository

public interface ServicioRepository extends JpaRepository<Servicio, Long> {

}
