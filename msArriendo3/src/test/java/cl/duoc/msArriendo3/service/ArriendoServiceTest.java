package cl.duoc.msArriendo3.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.msArriendo3.client.EmpleadoClient;
import cl.duoc.msArriendo3.client.ReservaClient;
import cl.duoc.msArriendo3.dto.ArriendoDTO;
import cl.duoc.msArriendo3.dto.EmpleadoDTO;
import cl.duoc.msArriendo3.dto.ReservaDTO;
import cl.duoc.msArriendo3.model.Arriendo;
import cl.duoc.msArriendo3.repository.ArriendoRepository;

@ExtendWith(MockitoExtension.class)
public class ArriendoServiceTest {

    @Mock
    private ArriendoRepository arriendoRepository;

    @Mock
    private EmpleadoClient empleadoClient;

    @Mock
    private ReservaClient reservaClient;

    @InjectMocks
    private ArriendoService arriendoService;

    private Arriendo arriendoEjemplo;
    private Date fechaInicio;
    private Date fechaFin;

    @BeforeEach
    void setUp() {
        fechaInicio = new Date();
        // Sumar un día para la fecha de fin simulada
        fechaFin = new Date(fechaInicio.getTime() + (1000 * 60 * 60 * 24));

        arriendoEjemplo = new Arriendo();
        arriendoEjemplo.setId(1);
        arriendoEjemplo.setFechaInicio(fechaInicio);
        arriendoEjemplo.setFechaFin(fechaFin);
        arriendoEjemplo.setReservaId(10);
        arriendoEjemplo.setEmpleadoId(20);
    }

    // ---------- ListarArriendo ----------

    @Test
    void listarArriendo_retornaLista() {
        // ARRANGE
        when(arriendoRepository.findAll()).thenReturn(List.of(arriendoEjemplo));

        // ACT
        List<Arriendo> resultado = arriendoService.ListarArriendo();

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getId());
    }

    // ---------- buscarPorId ----------

    @Test
    void buscarPorId_encontrado() {
        // ARRANGE
        when(arriendoRepository.findById(1)).thenReturn(Optional.of(arriendoEjemplo));

        // ACT
        Arriendo resultado = arriendoService.buscarPorId(1);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(10, resultado.getReservaId());
    }

    @Test
    void buscarPorId_noEncontrado() {
        // ARRANGE
        when(arriendoRepository.findById(99)).thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            arriendoService.buscarPorId(99);
        });

        assertEquals("Arriendo no encontrado", exception.getMessage());
    }

    // ---------- guardarArriendo / crearArriendo ----------

    @Test
    void guardarArriendo_exitoso() {
        // ARRANGE
        when(arriendoRepository.save(arriendoEjemplo)).thenReturn(arriendoEjemplo);

        // ACT
        Arriendo resultado = arriendoService.guardarArriendo(arriendoEjemplo);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    void crearArriendo_exitoso() {
        // ARRANGE
        when(arriendoRepository.save(arriendoEjemplo)).thenReturn(arriendoEjemplo);

        // ACT
        Arriendo resultado = arriendoService.crearArriendo(arriendoEjemplo);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    // ---------- actualizarArriendo ----------

    @Test
    void actualizarArriendo_exitoso() {
        // ARRANGE
        Date nuevaFechaInicio = new Date();
        Date nuevaFechaFin = new Date(nuevaFechaInicio.getTime() + (1000 * 60 * 60 * 48));

        Arriendo datosActualizar = new Arriendo();
        datosActualizar.setFechaInicio(nuevaFechaInicio);
        datosActualizar.setFechaFin(nuevaFechaFin);

        when(arriendoRepository.findById(1)).thenReturn(Optional.of(arriendoEjemplo));
        when(arriendoRepository.save(arriendoEjemplo)).thenReturn(arriendoEjemplo);

        // ACT
        Arriendo resultado = arriendoService.actualizarArriendo(1, datosActualizar);

        // ASSERT
        assertEquals(nuevaFechaInicio, resultado.getFechaInicio());
        assertEquals(nuevaFechaFin, resultado.getFechaFin());
        verify(arriendoRepository, times(1)).save(arriendoEjemplo);
    }

    @Test
    void actualizarArriendo_noEncontrado() {
        // ARRANGE
        when(arriendoRepository.findById(99)).thenReturn(Optional.empty());

        // ACT + ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            arriendoService.actualizarArriendo(99, arriendoEjemplo);
        });

        assertEquals("Arriendo no encontrado", exception.getMessage());
        verify(arriendoRepository, times(0)).save(any(Arriendo.class));
    }

    // ---------- eliminarArriendo ----------

    @Test
    void eliminarArriendo_exitoso() {
        // ARRANGE
        when(arriendoRepository.existsById(1)).thenReturn(true);

        // ACT & ASSERT
        assertDoesNotThrow(() -> arriendoService.eliminarArriendo(1));
        verify(arriendoRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarArriendo_noExiste() {
        // ARRANGE
        when(arriendoRepository.existsById(99)).thenReturn(false);

        // ACT + ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            arriendoService.eliminarArriendo(99);
        });

        assertEquals("Arriendo no encontrado", exception.getMessage());
        verify(arriendoRepository, times(0)).deleteById(99);
    }

    // ---------- obtenerArriendoDTO (Feign Clients Integration) ----------

    @Test
    void obtenerArriendoDTO_exitoso() {
        // ARRANGE: Mock del arriendo local
        when(arriendoRepository.findById(1)).thenReturn(Optional.of(arriendoEjemplo));

        // Mock de las respuestas de los clientes Feign externos
        ReservaDTO reservaSimulada = new ReservaDTO(); 
        // Asumiendo propiedades básicas de tus DTOs, ajústalas si es necesario
        EmpleadoDTO empleadoSimulado = new EmpleadoDTO();

        when(reservaClient.obtenerReservaPorId(10)).thenReturn(reservaSimulada);
        when(empleadoClient.obtenerEmpleadoPorId(20)).thenReturn(empleadoSimulado);

        // ACT
        ArriendoDTO resultado = arriendoService.obtenerArriendoDTO(1);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(fechaInicio.toString(), resultado.getFechaInicio());
        assertEquals(fechaFin.toString(), resultado.getFechaFin());
    }
}
