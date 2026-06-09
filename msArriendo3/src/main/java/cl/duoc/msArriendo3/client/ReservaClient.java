package cl.duoc.msArriendo3.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msArriendo3.dto.ReservaDTO;

@FeignClient(name = "msReserva")
public interface ReservaClient {

    @GetMapping("/api/v1/reservas/dto/{id}")
    ReservaDTO obtenerReservaPorId(@PathVariable Integer id);
}
