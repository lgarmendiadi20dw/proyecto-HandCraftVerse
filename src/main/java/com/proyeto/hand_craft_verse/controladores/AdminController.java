package com.proyeto.hand_craft_verse.controladores;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyeto.hand_craft_verse.aplicacion.AplicacionUsuario;
import com.proyeto.hand_craft_verse.aplicacion.IAplicacion;
import com.proyeto.hand_craft_verse.dominio.usuarios.Admin;
import com.proyeto.hand_craft_verse.dto.UserGetDto;
import com.proyeto.hand_craft_verse.dto.UserRegisterDto;

@RestController
@RequestMapping("/member/admin")
public class AdminController {
    @Autowired
    IAplicacion<Admin> aplicacionAdmin;
@Autowired
    // @Qualifier("getAplicacionUsuario")
    private AplicacionUsuario aplicacionUsuario;

@PostMapping("/registrar")
    public ResponseEntity<UserGetDto> registrar(@RequestBody UserRegisterDto user) {
        UserGetDto toReturn = aplicacionUsuario.guardarAdmin(user);
        if (toReturn != null) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(toReturn);

        } else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    @GetMapping("/{id}")
    public Admin viewMyPorfile(@PathVariable int id) {
        return aplicacionAdmin.buscar(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Admin> addAdmin(@RequestBody Admin Admin) {

        try {

            if (aplicacionAdmin.guardar(Admin)) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Admin);
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
    public ResponseEntity<Void> putMethodName(@PathVariable int id, @RequestBody Admin entity) {

        if (aplicacionAdmin.actualizar(entity) != null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @GetMapping("/all")
    public List<Admin> verAdminesList() {
        System.out.println(aplicacionAdmin.obtenerTodos());
        return aplicacionAdmin.obtenerTodos();
    }
}
