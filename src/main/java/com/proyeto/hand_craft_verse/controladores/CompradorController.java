package com.proyeto.hand_craft_verse.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyeto.hand_craft_verse.aplicacion.IAplicacion;
import com.proyeto.hand_craft_verse.dominio.Comprador;
import com.proyeto.hand_craft_verse.dominio.Usuario;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/member")
// @AllArgsConstructor
public class CompradorController {
    @Autowired
    IAplicacion<Comprador> aplicacionComprador;
    @Autowired
    IAplicacion<Usuario> aplicacionUsuario;

    @GetMapping("/{dni}")
    public Usuario viewMyPorfile(@PathVariable String dni) {
        return aplicacionComprador.buscar(dni);
    }

    @PostMapping("/create")
    public ResponseEntity<Comprador> addComprador(@RequestBody Comprador Comprador) {

        try {

            if (aplicacionComprador.guardar(Comprador)) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Comprador);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null);
            }

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);

        }

    }

    @DeleteMapping("/delete/{dni}")
    public ResponseEntity<Void> deleteUsuarioBydni(@PathVariable String dni) {
        if (aplicacionUsuario.eliminar(dni)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @PutMapping("/update/{dni}")
    public ResponseEntity<Void> putMethodName(@PathVariable String dni, @RequestBody Comprador entity) {

        if (aplicacionComprador.actualizar(entity) != null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @GetMapping("/all")
    public List<Comprador> verCompradoresList() {
        System.out.println(aplicacionComprador.obtenerTodos());
        return aplicacionComprador.obtenerTodos();
    }

}
