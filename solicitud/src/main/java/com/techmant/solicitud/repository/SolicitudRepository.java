package com.techmant.solicitud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techmant.solicitud.model.Solicitud;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud,Long>{

}
