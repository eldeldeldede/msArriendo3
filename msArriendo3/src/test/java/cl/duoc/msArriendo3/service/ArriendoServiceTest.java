package cl.duoc.msArriendo3.service;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.msArriendo3.model.Arriendo;
import cl.duoc.msArriendo3.repository.ArriendoRepository;

@ExtendWith(MockitoExtension.class)
public class ArriendoServiceTest {

    @Mock
    private ArriendoRepository arriendoRepository;

    @InjectMocks
    private ArriendoService arriendoService;

    private Arriendo arriendoEjemplo;

    @BeforeEach
    void setup(){

        arriendoEjemplo = new Arriendo();
        arriendoEjemplo.setEmpleadoId(1);
        arriendoEjemplo.setFechaInicio(null);
        arriendoEjemplo.setFechaFin(null);
        arriendoEjemplo.setReservaId(01);
    }
    @Test
    public void buscarPorId_encontrado(){
        //ARRANGE
        Optional<Arriendo> optionalArriendo = Optional.of(arriendoEjemplo);
        when(arriendoRepository.findById(1)).thenReturn(optionalArriendo);
        //ACT
        Arriendo resultado = arriendoService.buscarPorId(1);
        //ASSERT
        assertEquals(1, resultado.getId());
        assertEquals("19-06-2026", resultado.getFechaInicio());
        assertEquals("26-06-2026", resultado.getFechaFin());
    }

}
