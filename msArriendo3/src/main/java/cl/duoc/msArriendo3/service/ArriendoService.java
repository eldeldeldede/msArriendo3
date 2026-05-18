package cl.duoc.msArriendo3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msArriendo3.client.EmpleadoClient;
import cl.duoc.msArriendo3.client.ReservaClient;
import cl.duoc.msArriendo3.dto.ArriendoDTO;
import cl.duoc.msArriendo3.dto.EmpleadoDTO;
import cl.duoc.msArriendo3.dto.ReservaDTO;
import cl.duoc.msArriendo3.model.Arriendo;
import cl.duoc.msArriendo3.repository.ArriendoRepository;

@Service
public class ArriendoService {

    @Autowired
    private ArriendoRepository repo;

    @Autowired
    private EmpleadoClient empleadoClient;

    @Autowired
    private ReservaClient reservaClient;

    public List<Arriendo> ListarArriendo() {
        return repo.findAll();
    }

    public Arriendo buscarPorId(Integer id){
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Arriendo no encontrado"));
    }

    public Arriendo guardarArriendo(Arriendo arriendo){
        return repo.save(arriendo);
    }
    public Arriendo crearArriendo(Arriendo arriendo) {
        return repo.save(arriendo);
    }

    public Arriendo actualizarArriendo(Integer id, Arriendo arriendoActualizar) {
        Arriendo arriendo = repo.findById(id).orElseThrow(() -> new RuntimeException("Arriendo no encontrado"));
        arriendo.setFechaInicio(arriendoActualizar.getFechaInicio());
        arriendo.setFechaFin(arriendoActualizar.getFechaFin());

        return repo.save(arriendo);
    }

    public void eliminarArriendo(Integer id) {
        if(repo.existsById(id)){
            repo.deleteById(id);
        }else{
            throw new RuntimeException("Arriendo no encontrado");
        }
    }

    public ArriendoDTO obtenerArriendoDTO(Integer id) {
        Arriendo arriendo = buscarPorId(id);

        ReservaDTO reserva = reservaClient.obtenerReservaPorId(arriendo.getReservaId());
        EmpleadoDTO empleado = empleadoClient.obtenerEmpleadoPorId(arriendo.getEmpleadoId());

        ArriendoDTO arriendoCompleto = new ArriendoDTO();

        arriendoCompleto.setId(arriendo.getId());
        arriendoCompleto.setFechaInicio(arriendo.getFechaInicio().toString());
        arriendoCompleto.setFechaFin(arriendo.getFechaFin().toString());
        arriendoCompleto.setReserva(reserva);
        arriendoCompleto.setEmpleado(empleado);

        return arriendoCompleto;
    }
}
