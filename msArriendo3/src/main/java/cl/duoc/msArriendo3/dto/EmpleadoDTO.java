package cl.duoc.msArriendo3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDTO {

    private Integer id;
    private String nombre;
    private String rut;
}
