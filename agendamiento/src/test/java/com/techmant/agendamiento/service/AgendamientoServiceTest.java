package com.techmant.agendamiento.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.techmant.agendamiento.model.Agendamiento;
import com.techmant.agendamiento.repository.AgendamientoRepository;
import com.techmant.agendamiento.webusuario.UsuarioCat;

@ExtendWith(MockitoExtension.class)
public class AgendamientoServiceTest {

    
    //creamos una copia del repository con el mock ya que se necesita el acceso 
    @Mock
    private AgendamientoRepository agendamientoRepository;

    @Mock
    private UsuarioCat usuarioCat;

    //Con esto creamos una copia de objetos simulados para poder ejecutar 
    @InjectMocks
    private AgendamientoService agendamientoService;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Test
    void obtenerTodos_deberiaRetornarLista() throws Exception {
        
        Date fecha = sdf.parse("09/07/2025");
        List<Agendamiento> lista = List.of(new Agendamiento(1L, "Activo", fecha, "Prueba", 1L));

        when(agendamientoRepository.findAll()).thenReturn(lista);

        List<Agendamiento> resultado = agendamientoService.getAgendamientos();

        assertThat(resultado).isEqualTo(lista);
    }



    //Test para obtener asignación por ID
    @Test
    void obtenerPorId_deberiaRetornarAgendamiento() throws Exception {
        
        Date fecha = sdf.parse("09/07/2025");
        Agendamiento agenda = new Agendamiento(2L, "Pendiente", fecha, "Revisión", 2L);

        when(agendamientoRepository.findById(2L)).thenReturn(Optional.of(agenda));

        Agendamiento resultado = agendamientoService.getAgendamientoById(2L);

        assertThat(resultado).isEqualTo(agenda);
    }



    @Test
    void guardarAgendamiento_deberiaGuardarSiUsuarioExiste() throws Exception {
        
        Date fecha = sdf.parse("09/07/2025");
        Agendamiento nuevo = new Agendamiento(null, "Agendado", fecha, "Observación", 5L);

        when(usuarioCat.obtenerUsuarioPorId(5L)).thenReturn(Map.of("id", 5L));
        when(agendamientoRepository.save(nuevo)).thenReturn(nuevo);

        Agendamiento resultado = agendamientoService.agregarAgendamiento(nuevo);

        assertThat(resultado).isEqualTo(nuevo);
    }


    
    @Test
    void eliminarPorId_deberiaLlamarADelete() {
        Long id = 10L;
        agendamientoService.eliminarAgendamiento(id);

        verify(agendamientoRepository).deleteById(id);
    }


}
