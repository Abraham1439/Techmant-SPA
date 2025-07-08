package com.techmant.agendamiento.webusuario;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
 
@Component
public class UsuarioCat {

    //variable para la comunicacion 
    private final WebClient webclient;

    //metodo constructor de la clase
    public UsuarioCat(@Value("${usuarios-service.url}")String usuarioServiceUrl) {
        this.webclient = WebClient.builder().baseUrl(usuarioServiceUrl).build();
    }

    //metodo para comunicarnos con el microservicio de Usuario y buscar  si un Usuario existe mediante su id 
    public Map<String, Object> obtenerUsuarioPorId(Long id) {
        return this.webclient.get().uri("/{id}", id).retrieve().onStatus(status -> status.is4xxClientError() , response -> response.bodyToMono(String.class).map(body -> new RuntimeException("Usuario no encontrado"))).bodyToMono(Map.class).block();
    }

}
