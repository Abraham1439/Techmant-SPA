package com.techmant.agendamiento.websolicitud;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class SolicitudCat {

    //variable para la comunicacion 
    private final WebClient webclient;

    //metodo constructor de la clase
    public SolicitudCat(@Value("${solicitudes-service.url}")String solicitudServiceUrl) {
        this.webclient = WebClient.builder().baseUrl(solicitudServiceUrl).build();
    }

    //metodo para comunicarnos con el microservicio de una solicitud y buscar  si una solicitud existe mediante su id 
    public Map<String, Object> obtenerSolicitudPorId(Long id) {
        return this.webclient.get().uri("/{id}", id).retrieve().onStatus(status -> status.is4xxClientError() , response -> response.bodyToMono(String.class).map(body -> new RuntimeException("Solicitud no encontrada"))).bodyToMono(Map.class).block();
    }

}
