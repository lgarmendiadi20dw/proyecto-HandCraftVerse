package com.proyeto.hand_craft_verse.dominio.usuarios;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue
    private int id;

    private String dni;

    @Column(nullable = false)
    private String nombre_usuario;

    private String nombre;

    private String apellidos;
    // private int edad;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(name = "contrasena", nullable = false, length = 60)
    private String contrasena;

    private String imagen;

    @Column(unique = true)
    private int telefono;

    public Usuario(String dni, String nombre_usuario, String nombre, String apellidos, String email,
            String contrasena, int telefono) {
        this.dni = dni;
        this.nombre_usuario = nombre_usuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.contrasena = contrasena;
        this.telefono = telefono;
    }

    public Usuario(String nombre_usuario, String email,
            String contrasena, int telefono) {
        this.nombre_usuario = nombre_usuario;
        this.email = email;
        this.contrasena = contrasena;
        this.telefono = telefono;
    }
}
