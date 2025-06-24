package com.TechMant.rol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TechMant.rol.model.Rol;
import com.TechMant.rol.repository.RolRepository;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> getAllRoles(){
        return rolRepository.findAll();
    }

    public Rol getRolById(Long idRol){
        return rolRepository.findById(idRol).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    }

    public Rol createRol(Rol rol){
        return rolRepository.save(rol);
    }

    public Rol updateRol(Long idRol, Rol rol){
        Rol rolExistente = rolRepository.findById(idRol).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        rolExistente.setNombreRol(rol.getNombreRol());
        return rolRepository.save(rolExistente);
    }

    public void deleteRol(Long idRol){
        rolRepository.deleteById(idRol);
    }
 }
