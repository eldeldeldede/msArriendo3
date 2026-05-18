package cl.duoc.msArriendo3.model;

import java.util.Date;

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
    private Integer id;
    
    @Column(nullable = false)
    private Date fechaInicio;
    
    @Column(nullable = false)
    private Date fechaFin;

    @Column(name = "reserva_id", nullable = false)
    private Integer reservaId;

    @Column(name = "empleado_id", nullable = false)
    private Integer empleadoId;
}
