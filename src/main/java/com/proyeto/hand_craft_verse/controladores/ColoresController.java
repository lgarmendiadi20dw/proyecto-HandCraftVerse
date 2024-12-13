package com.proyeto.hand_craft_verse.controladores;

import com.proyeto.hand_craft_verse.aplicacion.IAplicacion;
import com.proyeto.hand_craft_verse.dominio.productos.Colore;
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

            if (aplicacionColore.actualizar(existingColor) ) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/createAllDefaultColors")
public ResponseEntity<List<Colore>> addDefaultColors() {
    try {
        // Lista de colores básicos por defecto
        List<Colore> defaultColors = List.of(
            new Colore("#FF0000", "Rojo"),
            new Colore("#00FF00", "Verde"),
            new Colore("#0000FF", "Azul"),
            new Colore("#FFFF00", "Amarillo"),
            new Colore("#FFA500", "Naranja"),
            new Colore("#800080", "Morado"),
            new Colore("#000000", "Negro"),
            new Colore("#FFFFFF", "Blanco"),
            new Colore("#808080", "Gris"),
            new Colore("#FFC0CB", "Rosa")
        );

        // Guardar los colores en la base de datos
        List<Colore> savedColors = defaultColors.stream()
            .filter(aplicacionColore::guardar) // Guardar y filtrar los que se guardan con éxito
            .toList();

        if (!savedColors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedColors);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}

    @GetMapping("/all")
    public List<Colore> verColoresList() {
        return aplicacionColore.obtenerTodos();

    }
}
