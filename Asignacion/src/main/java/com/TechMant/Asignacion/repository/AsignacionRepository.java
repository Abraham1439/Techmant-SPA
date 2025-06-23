package com.TechMant.Asignacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TechMant.Asignacion.model.Asignacion;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {

}
