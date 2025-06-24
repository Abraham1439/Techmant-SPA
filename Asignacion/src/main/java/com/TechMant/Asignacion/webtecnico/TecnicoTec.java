package com.TechMant.Asignacion.webtecnico;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class TecnicoTec {

    //variable para la comunicacion 
    private final WebClient webclient;

    //metodo constructor de la clase 
    public TecnicoTec(@Value("${tecnico-service.url}") String categoriaServiceUrl) {
        this.webclient = WebClient.builder().baseUrl(categoriaServiceUrl).build();
    }

    //metodo para comunicarnos con el microservicio de una categoria y buscar  si una categoria existe mediante su id 
    public Map<String, Object> obtenerTecnicoPorId(Long id) {
        return this.webclient.get().uri("/{id}", id).retrieve().onStatus(status -> status.is4xxClientError() , response -> response.bodyToMono(String.class).map(body -> new RuntimeException("Tecnico no encontrado"))).bodyToMono(Map.class).block();
    }

}
