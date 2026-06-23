package cl.duoc.msArriendo3.model;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "arriendo")
public class Arriendo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Schema(description = "Identificador único del arriendo", example = "1")
    private Integer id;
    
    @Column(nullable = false)
    @Schema(description = "Fecha de inicio del arriendo", example = "2023-01-01")
    private Date fechaInicio;
    
    @Column(nullable = false)
    @Schema(description = "Fecha de fin del arriendo", example = "2023-01-01")
    private Date fechaFin;

    @Column(name = "reserva_id", nullable = false)
    @Schema(description = "Identificador de la reserva asociada", example = "1")
    private Integer reservaId;

    @Column(name = "empleado_id", nullable = false)
    @Schema(description = "Identificador del empleado encargado", example = "1")
    private Integer empleadoId;
}
