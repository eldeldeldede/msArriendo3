package cl.duoc.msArriendo3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msArriendo3.dto.ArriendoDTO;
import cl.duoc.msArriendo3.model.Arriendo;
import cl.duoc.msArriendo3.service.ArriendoService;

@RestController
@RequestMapping("api/v1/arriendo")
public class ArriendoController {

    @Autowired
    private ArriendoService arriendoService;

    @GetMapping
    public ResponseEntity<List<Arriendo>> listar(){
        try {
            List<Arriendo> arriendos = arriendoService.ListarArriendo();
            return ResponseEntity.ok(arriendos);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Arriendo> guardarArriendo(Arriendo arriendo){
        try {
            Arriendo nuevoArriendo = arriendoService.guardarArriendo(arriendo);
            return ResponseEntity.ok(nuevoArriendo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Arriendo> buscarArriendo(@PathVariable Integer id){
        try {
            Arriendo arriendo = arriendoService.buscarPorId(id);
            return ResponseEntity.ok(arriendo);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Arriendo> actualizarArriendo(@PathVariable Integer id, @RequestBody Arriendo arriendoActualizado){
        try {
            Arriendo arriendoActualizado1 = arriendoService.actualizarArriendo(id, arriendoActualizado);
            return ResponseEntity.ok(arriendoActualizado1);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> eliminarArriendo(Integer id){
        try {
            arriendoService.eliminarArriendo(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")

    public ResponseEntity<ArriendoDTO> detalleArriendoDTO(@PathVariable Integer id) {
        try {
            ArriendoDTO arriendoDTO = arriendoService.obtenerArriendoDTO(id);
            return ResponseEntity.ok(arriendoDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
