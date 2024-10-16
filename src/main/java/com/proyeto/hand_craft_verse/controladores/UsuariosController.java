package com.proyeto.hand_craft_verse.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyeto.hand_craft_verse.aplicacion.IAplicacion;
import com.proyeto.hand_craft_verse.dominio.Usuario;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/usuario")
public class UsuariosController {
    IAplicacion<Usuario> aplicacionUsuario;
    @GetMapping("/member/{id}")
    public Usuario viewMyPorfile(@PathVariable int id) {
       return aplicacionUsuario.buscar(id);
    }
    
}
