package com.TechMantSPA.equipos.client;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.TechMantSPA.equipos.dto.UsuarioDTO;

@Service
public class UsuarioClient {

    private final RestTemplate restTemplate;
    private static final String USUARIO_SERVICE_URL = "http://localhost:8081/api/v1/usuarios";

    public UsuarioClient(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public UsuarioDTO getUsuarioById(Long duenioEquipo){
        try {
            String url = USUARIO_SERVICE_URL + "/" + duenioEquipo;
            return restTemplate.getForObject(url, UsuarioDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println("Usuario no encontrado con ID: "+ duenioEquipo);
            return null;
        } catch (HttpClientErrorException | ResourceAccessException e){

            System.out.println("Error al obtener el usuario: " + e.getMessage());
            return null;
        }
    }

    public UsuarioDTO getUsuarioByCorreo(String correo) {
        try {
            String url = USUARIO_SERVICE_URL + "/correo/" + correo;
            return restTemplate.getForObject(url, UsuarioDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println("Usuario no encontrado con correo: " + correo);
            return null;
        } catch (HttpClientErrorException | ResourceAccessException e) {
            System.out.println("Error al obtener usuario: " + e.getMessage());
            return null;
        }
    }

}
