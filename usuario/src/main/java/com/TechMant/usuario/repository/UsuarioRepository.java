package com.TechMant.usuario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TechMant.usuario.model.Usuario;

/**
 * Repositorio para la entidad Usuario.
 * Proporciona métodos para acceder y manipular datos de usuarios en la base de datos.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    /**
     * Busca todos los usuarios que tienen un rol específico.
     *
     * @param idRol ID del rol por el cual filtrar los usuarios
     * @return Lista de usuarios que tienen el rol especificado
     */
    List<Usuario> findByIdRol(Integer idRol); 
    boolean existsByCorreo(String correo);
    Optional<Usuario> findByCorreo(String correo);

}

