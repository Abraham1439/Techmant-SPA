package com.techmant.Gestion_tecnico.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;


import com.techmant.Gestion_tecnico.model.Tecnico;
import com.techmant.Gestion_tecnico.repository.TecnicoRepository;

@ExtendWith(MockitoExtension.class)
public class TecnicoServiceTest {

    
   @Mock
    private TecnicoRepository repository;

    @InjectMocks
    private TecnicoService service;


    @Test
    void crearTecnico_devuelveTecnicoGuardado() {
    // Datos simulados de entrada y salida
    Tecnico entrada = new Tecnico(null, "Carlos Nuevo", "Soporte");
    Tecnico guardado = new Tecnico(1L, "Carlos Nuevo", "Soporte");

    // Configurar comportamiento simulado del repositorio
    when(repository.save(entrada)).thenReturn(guardado);

    // Ejecutar método del servicio
    Tecnico resultado = service.crearTecnico(entrada);

    // Validar resultado
    assertThat(resultado.getId()).isEqualTo(1L);
    assertThat(resultado.getNombre()).isEqualTo("Carlos Nuevo");
    assertThat(resultado.getEspecialidad()).isEqualTo("Soporte");

    // Verificar que se llamó al método save del repositorio
    verify(repository).save(entrada);

    }

   @Test
   void obtenerTodosLosTecnicos_retornaListaDesdeRepositorio() {
    // Creamos una lista que simula la respuesta del repositorio
    List<Tecnico> mockList = Arrays.asList(
        new Tecnico(1L, "Carlos Soto", "Electricidad"),
        new Tecnico(2L, "María Pérez", "Redes"));

    // Definimos el comportamiento del repositorio simulado
    when(repository.findAll()).thenReturn(mockList);

    // Llamamos al método del servicio que vamos a testear
    List<Tecnico> resultado = service.obtenerTodosLosTecnicos();

    // Verificamos que el resultado sea exactamente la lista simulada
    assertThat(resultado).isEqualTo(mockList);
   
    }
    
    @Test
    void obtenerTecnicoPorId_devuelveTecnicoSiExiste() {
    // Técnico simulado
    Tecnico tecnico = new Tecnico(1L, "Luis Rojas", "Mecánica");

    // Aunque no uses Optional directamente, findById lo retorna, así que en el mock sí se usa
    when(repository.findById(1L)).thenReturn(java.util.Optional.of(tecnico));

    // Llamar al método
    Tecnico resultado = service.obtenerTecnicoPorId(1L);

    // Verificar resultado
    assertThat(resultado).isNotNull();
    assertThat(resultado.getId()).isEqualTo(1L);
    assertThat(resultado.getNombre()).isEqualTo("Luis Rojas");
    assertThat(resultado.getEspecialidad()).isEqualTo("Mecánica");

    // Verificar que el método del repositorio fue llamado
    verify(repository).findById(1L);
}

    @Test
    void actualizarTecnico_existente_devuelveActualizado() {
        Tecnico datosActualizados = new Tecnico(null, "Pedro Actualizado", "Redes");
        Tecnico esperado = new Tecnico(1L, "Pedro Actualizado", "Redes");

        when(repository.existsById(1L)).thenReturn(true);
        when(repository.save(any(Tecnico.class))).thenReturn(esperado);

        Tecnico resultado = service.actualizarTecnico(1L, datosActualizados);

        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNombre()).isEqualTo("Pedro Actualizado");
        verify(repository).save(datosActualizados);
    }

    @Test
    void eliminarTecnico_existente_llamaDeleteById() {
        when(repository.existsById(1L)).thenReturn(true);

        service.eliminarTecnico(1L);

        verify(repository).deleteById(1L);
    }
}
