package com.TechMantSPA.equipos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TechMantSPA.equipos.model.Equipos;

@Repository
public interface EquiposRepository extends JpaRepository<Equipos, Long> {

    List<Equipos> findByIdUsuario(Long idUsuario);
    List<Equipos> findByTipoDeDispositivoAndIdUsuario(String tipo, Long idUsuario);

    List<Equipos> findByTipoDeDispositivo(String tipo);

}
