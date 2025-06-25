package com.techmant.Gestion_resena.webusuario;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class UsuarioCat {
    private final WebClient webClient;

    public UsuarioCat(@Value("${usuarios-service.url}") String usuariosServiceUrl){
        this.webClient =WebClient.builder().baseUrl(usuariosServiceUrl).build();
    }

    public Map<String, Object> getUsuarioById(Long id) {
        return this.webClient.get().uri("/{id}", id).retrieve().onStatus(status -> status.is4xxClientError() , response -> response.bodyToMono(String.class).map(body -> new RuntimeException("usuario no encontrada"))).bodyToMono(Map.class).block();
    }
}
