package com.proyeto.hand_craft_verse.controladores.usuarios;

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

   


    @GetMapping("/all")
    public List<Admin> verAdminesList() {
        System.out.println(aplicacionAdmin.obtenerTodos());
        return aplicacionAdmin.obtenerTodos();
    }
}
