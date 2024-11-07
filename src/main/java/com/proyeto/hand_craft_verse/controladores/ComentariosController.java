package com.proyeto.hand_craft_verse.controladores;

import com.proyeto.hand_craft_verse.aplicacion.IAplicacion;
import com.proyeto.hand_craft_verse.dominio.productos.Comentario;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comentarios")
public class ComentariosController {

    @Autowired
    IAplicacion<Comentario> aplicacionComentario;

    @GetMapping("/{id}")
    public Comentario viewComentario(@PathVariable int id) {
        return aplicacionComentario.buscar(id);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteComentarioById(@PathVariable int id) {
        if (aplicacionComentario.eliminar(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Comentario> addComentario(@RequestBody Comentario comentario) {
        try {
            if (aplicacionComentario.guardar(comentario)) {
                return ResponseEntity.status(HttpStatus.CREATED).body(comentario);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateComentario(@PathVariable int id, @RequestBody Comentario comentario) {
        Comentario existingComentario = aplicacionComentario.buscar(id);
        if (existingComentario != null) {
            existingComentario.setTexto(comentario.getTexto());
            existingComentario.setValoracion(comentario.getValoracion());

            if (aplicacionComentario.actualizar(existingComentario) != null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    public List<Comentario> verComentarioesList() {
        System.out.println(aplicacionComentario.obtenerTodos());
        return aplicacionComentario.obtenerTodos();
    }
}
