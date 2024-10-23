package com.proyeto.hand_craft_verse.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDTO {
    private String dni;
    private String nombreUsuario;
    private String nombre;
    private String apellidos;
    private String email;
    private String imagen;
    private String contrasena;
    private int telefono;

    public UsuarioDTO(String dni, String nombreUsuario, String nombre, String apellidos, String email,
            String contrasena, int telefono) {
        this.dni = dni;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.contrasena = contrasena;
        this.telefono = telefono;
    }

    public UsuarioDTO(String nombreUsuario, String email,
            String contrasena, int telefono) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contrasena;
        this.telefono = telefono;
    }
}
