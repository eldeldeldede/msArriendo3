package cl.duoc.msArriendo3.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msArriendo3.dto.ClienteDTO;

@FeignClient(name = "msCliente", url = "http://localhost:8083")
public interface ClienteClient {

    @GetMapping("/api/v1/cliente/dto/{id}")
    ClienteDTO obtenerClientePorId(@PathVariable Integer id);
}
