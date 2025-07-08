package com.TechMantSPA.equipos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TechMantSPA.equipos.model.Equipos;

@Repository
public interface EquiposRepository extends JpaRepository<Equipos, Long> {

    List<Equipos> findByIdDuenoEquipo(Long idDuenoEquipo);
    List<Equipos> findByTipoDeDispositivoAndIdDuenoEquipo(String tipo, Long idDuenoEquipo);

    List<Equipos> findByTipoDeDispositivo(String tipo);

}
