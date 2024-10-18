package com.proyeto.hand_craft_verse.dominio.usuarios;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Admin extends Usuario {
    private int nivel;

    public Admin(String dni, String nombre_usuario, String nombre, String apellidos, String email,
            String contrasena, int telefono, int nivel) {
        super(dni, nombre_usuario, nombre, apellidos, email, contrasena, telefono);
        this.nivel = nivel;
    }
}
