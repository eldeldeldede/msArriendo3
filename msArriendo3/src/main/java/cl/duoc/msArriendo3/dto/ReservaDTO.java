package cl.duoc.msArriendo3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {

    private Integer id;
    private ClienteDTO cliente;
}
