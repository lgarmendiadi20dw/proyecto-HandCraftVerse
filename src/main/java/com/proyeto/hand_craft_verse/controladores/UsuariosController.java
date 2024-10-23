package com.proyeto.hand_craft_verse.controladores;

import com.proyeto.hand_craft_verse.aplicacion.IAplicacion;
import com.proyeto.hand_craft_verse.dominio.usuarios.Usuario;
import com.proyeto.hand_craft_verse.dominio.usuarios.Usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/member")
public class UsuariosController {
    @Autowired
    IAplicacion<Usuario> aplicacionUsuario;

    @GetMapping("/{id}")
    public Usuario viewMyPorfile(@PathVariable int id) {
        return aplicacionUsuario.buscar(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUsuarioByid(@PathVariable int id) {
        if (aplicacionUsuario.eliminar(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario Usuario) {

        try {

            if (aplicacionUsuario.guardar(Usuario)) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Usuario);
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
    public ResponseEntity<Void> putMethodName(@PathVariable int id, @RequestBody Usuario entity) {

        Usuario Usuario = aplicacionUsuario.buscar(id);
        Usuario.setNombre(entity.getNombre());
        Usuario.setApellidos(entity.getApellidos());
        Usuario.setContrasena(entity.getContrasena());
        Usuario.setNombre_usuario(entity.getNombre_usuario());
        Usuario.setTelefono(entity.getTelefono());
        Usuario.setEmail(entity.getEmail());

        if (aplicacionUsuario.actualizar(entity) != null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @GetMapping("/all")
    public List<Usuario> verUsuarioesList() {
        System.out.println(aplicacionUsuario.obtenerTodos());
        return aplicacionUsuario.obtenerTodos();
    }
}
