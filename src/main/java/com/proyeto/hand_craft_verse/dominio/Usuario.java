package com.proyeto.hand_craft_verse.dominio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Usuarios")
public class Usuario {

    @Id
    private String dni; //reemplazar id por dni

    @Column(nullable = false)
    private String nombre_usuario;

    private String nombre;

    private String apellidos;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(name = "contrasena", nullable = false, length = 60)
    private String contrasena;

    private String imagen;

    @Column(unique = true)
    private int telefono;
}
