package com.example.ticket.client;


import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.example.ticket.dto.UsuarioDTO;

@Service
public class UsuarioClient {
    private final RestTemplate restTemplate;
    private static final String USUARIO_SERVICE_URL = "http://localhost:8081/api/v1/usuarios"; // Usuario en 8081

    public UsuarioClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UsuarioDTO getUsuarioById(Long idUsuario) {
        try {
            String url = USUARIO_SERVICE_URL + "/" + idUsuario;
            return restTemplate.getForObject(url, UsuarioDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println("Usuario no encontrado con ID: " + idUsuario);
            return null;
        } catch (HttpClientErrorException | ResourceAccessException e) {
            System.out.println("Error al obtener el usuario: " + e.getMessage());
            return null;
        }
    }
}
