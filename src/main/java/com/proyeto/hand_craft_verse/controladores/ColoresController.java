package com.proyeto.hand_craft_verse.controladores;

import com.proyeto.hand_craft_verse.aplicacion.IAplicacion;
import com.proyeto.hand_craft_verse.dominio.productos.Colore;
import com.proyeto.hand_craft_verse.dto.ColoreDTO;
import com.proyeto.hand_craft_verse.dto.DtoConverter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/colores")
public class ColoresController {

    @Autowired
    IAplicacion<Colore> aplicacionColore;

    @GetMapping("/{hex}")
    public ResponseEntity<Colore> viewColor(@PathVariable String hex) {
        Colore color = aplicacionColore.buscar(hex);
        if (color != null) {
            return ResponseEntity.ok(color);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{hex}")
    public ResponseEntity<Void> deleteColorByHex(@PathVariable String hex) {
        if (aplicacionColore.eliminar(hex)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Colore> addColor(@RequestBody Colore color) {
        try {
            if (aplicacionColore.guardar(color)) {
                return ResponseEntity.status(HttpStatus.CREATED).body(color);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/update/{hex}")
    public ResponseEntity<Void> updateColor(@PathVariable String hex, @RequestBody Colore color) {
        Colore existingColor = aplicacionColore.buscar(hex);
        if (existingColor != null) {
            existingColor.setNombre(color.getNombre());
            // No se puede actualizar el 'hex' ya que es la clave primaria

            if (aplicacionColore.actualizar(existingColor) != null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    public List<ColoreDTO> verColoresList() {
        List<ColoreDTO> respuesta = new ArrayList<>(); // Inicializa la lista
        List<Colore> colores = aplicacionColore.obtenerTodos(); // Supongamos que este es el método para obtener todos
                                                                // los colores

        try {
            for (Colore colore : colores) {
                respuesta.add(DtoConverter.fromColore(colore)); // Convertir cada Colore a ColoreDTO
            }
        } catch (Exception e) {
            // Manejo de la excepción (puedes registrar el error)
            e.printStackTrace(); // O puedes usar un logger
        }

        return respuesta;
    }
}
