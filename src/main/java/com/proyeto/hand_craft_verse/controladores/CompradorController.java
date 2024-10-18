package com.proyeto.hand_craft_verse.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyeto.hand_craft_verse.aplicacion.IAplicacion;
import com.proyeto.hand_craft_verse.dominio.usuarios.Comprador;
import com.proyeto.hand_craft_verse.dominio.usuarios.Usuario;

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
@RequestMapping("/member/customer")
// @AllArgsConstructor
public class CompradorController {
    @Autowired
    IAplicacion<Comprador> aplicacionComprador;
    

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

  

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> putMethodName(@PathVariable int id, @RequestBody Comprador entity) {

        
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
