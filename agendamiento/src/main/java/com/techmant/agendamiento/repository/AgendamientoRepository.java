package com.techmant.agendamiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techmant.agendamiento.model.Agendamiento;

@Repository
public interface AgendamientoRepository extends JpaRepository<Agendamiento, Long> {

}
