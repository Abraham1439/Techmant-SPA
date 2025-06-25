package com.TechMant.Asignacion.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.TechMant.Asignacion.model.Asignacion;
import com.TechMant.Asignacion.repository.AsignacionRepository;
import com.TechMant.Asignacion.webtecnico.TecnicoTec;

@ExtendWith(MockitoExtension.class)
public class AsignacionServiceTest {

    //creamos una copia del repository con el mock ya que se necesita el acceso 
    @Mock
    private AsignacionRepository repository;

    @Mock 
    private TecnicoTec tecnicoTec;

    //Con esto creamos una copia de objetos simulados para poder ejecutar 
    @InjectMocks
    private AsignacionService service;


    @Test
    void saveAsignacion_deberiaGuardarAsignacionSiTecnicoExiste() {
        // Arrange
        Asignacion asignacion = new Asignacion(1L, "Juan Pérez", "Reparación", 5L);
        Map<String, Object> tecnicoMock = new HashMap<>();

        tecnicoMock.put("id", 5L);

        tecnicoMock.put("nombre", "Juan Pérez");

        when(tecnicoTec.obtenerTecnicoPorId(5L)).thenReturn(tecnicoMock);
        when(repository.save(asignacion)).thenReturn(asignacion);

        Asignacion resultado = service.saveAsignacion(asignacion);

        assertThat(resultado).isEqualTo(asignacion);
    }


    //Test para obtener todas las asignaciones
    @Test
    void obtenerAsignaciones_deberiaRetornarListaDeAsignaciones() {
        Asignacion asignacion = new Asignacion(1L, "Carlos", "Instalación", 2L);
        when(repository.findAll()).thenReturn(java.util.List.of(asignacion));

        var resultado = service.obtenerAsignaciones();
    
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombreAsignado()).isEqualTo("Carlos");
    }



    //Test para obtener asignación por ID
    @Test
    void obtenerAsignacionporId_deberiaRetornarAsignacionSiExiste() {
    
        Asignacion asignacion = new Asignacion(1L, "Luisa", "Diagnóstico", 4L);
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(asignacion));

    
        Asignacion resultado = service.obtenerAsignacionporId(1L);

    
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombreCaso()).isEqualTo("Diagnóstico");
    }


    //Test para eliminar asignación por ID
    @Test
    void eliminarAsignacionPorId_deberiaEliminarAsignacionSiExiste() {
    
        Long id = 10L;
        Asignacion asignacion = new Asignacion(id, "Pedro", "Soporte", 3L);
        when(repository.findById(id)).thenReturn(java.util.Optional.of(asignacion));

        service.eliminarAsignacionPorId(id);

        verify(repository).deleteById(id);
    }

}
