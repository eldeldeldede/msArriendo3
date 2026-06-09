package cl.duoc.msArriendo3.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msArriendo3.dto.EmpleadoDTO;

@FeignClient(name = "msEmpleado")
public interface EmpleadoClient {

    @GetMapping("api/v1/empleado/dto/{id}")
    EmpleadoDTO obtenerEmpleadoPorId(@PathVariable Integer id);
}
