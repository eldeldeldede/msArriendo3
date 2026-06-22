package cl.duoc.msArriendo3.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.duoc.msArriendo3.dto.ArriendoDTO;
import cl.duoc.msArriendo3.model.Arriendo;
import cl.duoc.msArriendo3.service.ArriendoService;

@WebMvcTest(ArriendoController.class)
public class ArriendoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ArriendoService arriendoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Arriendo arriendoEjemplo;

    @BeforeEach
    void setUp() {
        arriendoEjemplo = new Arriendo();
        arriendoEjemplo.setId(1);
        arriendoEjemplo.setFechaInicio(new Date());
        arriendoEjemplo.setFechaFin(new Date());
        arriendoEjemplo.setReservaId(10);
        arriendoEjemplo.setEmpleadoId(20);
    }

    @Test
    void listar_retornaListaYOk() throws Exception {
        when(arriendoService.ListarArriendo()).thenReturn(List.of(arriendoEjemplo));

        mockMvc.perform(get("/api/v1/arriendo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].reservaId").value(10));
    }

    @Test
    void listar_error_retornaNotFound() throws Exception {
        when(arriendoService.ListarArriendo()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/v1/arriendo"))
                .andExpect(status().isNotFound());
    }

    @Test
    void guardarArriendo_exitoso_retornaOk() throws Exception {
        when(arriendoService.guardarArriendo(any(Arriendo.class))).thenReturn(arriendoEjemplo);

        mockMvc.perform(post("/api/v1/arriendo")
                .param("reservaId", "10")
                .param("empleadoId", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void guardarArriendo_error_retornaBadRequest() throws Exception {
        when(arriendoService.guardarArriendo(any(Arriendo.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post("/api/v1/arriendo"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void buscarArriendo_encontrado_retornaOk() throws Exception {
        when(arriendoService.buscarPorId(1)).thenReturn(arriendoEjemplo);

        mockMvc.perform(get("/api/v1/arriendo/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void buscarArriendo_noEncontrado_retornaNotFound() throws Exception {
        when(arriendoService.buscarPorId(99)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/v1/arriendo/id/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void actualizarArriendo_exitoso_retornaOk() throws Exception {
        Arriendo editado = new Arriendo();
        editado.setId(1);
        editado.setReservaId(10);
        editado.setEmpleadoId(20);

        when(arriendoService.actualizarArriendo(eq(1), any(Arriendo.class))).thenReturn(editado);

        String jsonCuerpo = objectMapper.writeValueAsString(editado);

        mockMvc.perform(put("/api/v1/arriendo/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCuerpo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void actualizarArriendo_error_retornaBadRequest() throws Exception {
        when(arriendoService.actualizarArriendo(eq(99), any(Arriendo.class))).thenThrow(new RuntimeException());

        String jsonCuerpo = objectMapper.writeValueAsString(arriendoEjemplo);

        mockMvc.perform(put("/api/v1/arriendo/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCuerpo))
                .andExpect(status().isBadRequest());
    }

    @Test
    void eliminarArriendo_exitoso_retornaOk() throws Exception {
        doNothing().when(arriendoService).eliminarArriendo(1);

        mockMvc.perform(delete("/api/v1/arriendo/id/1"))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarArriendo_noExiste_retornaNotFound() throws Exception {
        doThrow(new RuntimeException()).when(arriendoService).eliminarArriendo(99);

        mockMvc.perform(delete("/api/v1/arriendo/id/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void detalleArriendoDTO_encontrado_retornaOk() throws Exception {
        ArriendoDTO dto = new ArriendoDTO();
        dto.setId(1);
        dto.setFechaInicio(new Date().toString());
        dto.setFechaFin(new Date().toString());

        when(arriendoService.obtenerArriendoDTO(1)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/arriendo/dto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void detalleArriendoDTO_noEncontrado_retornaNotFound() throws Exception {
        when(arriendoService.obtenerArriendoDTO(99)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/v1/arriendo/dto/99"))
                .andExpect(status().isNotFound());
    }
}