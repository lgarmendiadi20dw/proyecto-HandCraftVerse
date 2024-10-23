package com.proyeto.hand_craft_verse.controladores;

import com.proyeto.hand_craft_verse.aplicacion.IAplicacion;
import com.proyeto.hand_craft_verse.dominio.direccion.Direccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/direcciones")
public class DireccionesController {

    @Autowired
    IAplicacion<Direccion> aplicacionDireccion;

    @GetMapping("/{id}")
    public ResponseEntity<Direccion> viewDireccion(@PathVariable int id) {
        Direccion direccion = aplicacionDireccion.buscar(id);
        if (direccion != null) {
            return ResponseEntity.ok(direccion);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDireccionById(@PathVariable int id) {
        if (aplicacionDireccion.eliminar(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Direccion> addDireccion(@RequestBody Direccion direccion) {
        try {
            if (aplicacionDireccion.guardar(direccion)) {
                return ResponseEntity.status(HttpStatus.CREATED).body(direccion);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateDireccion(@PathVariable int id, @RequestBody Direccion direccion) {
        Direccion existingDireccion = aplicacionDireccion.buscar(id);
        if (existingDireccion != null) {
            existingDireccion.setPais(direccion.getPais());
            existingDireccion.setProvincia(direccion.getProvincia());
            existingDireccion.setMunicipio(direccion.getMunicipio());
            existingDireccion.setCalle(direccion.getCalle());
            existingDireccion.setPrefijo(direccion.getPrefijo());
            existingDireccion.setNumTelefono(direccion.getNumTelefono());
            existingDireccion.setDestinatario(direccion.getDestinatario());
            existingDireccion.setTipoDireccion(direccion.getTipoDireccion());

            if (aplicacionDireccion.actualizar(existingDireccion) != null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    public List<Direccion> verDireccionesList() {
        return aplicacionDireccion.obtenerTodos();
    }
}
