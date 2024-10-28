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

    public Admin(String dni, String username, String nombre, String apellidos, String email,
            String password, int telefono, int nivel) {
        super(dni, username, nombre, apellidos, email, password, telefono);
        this.nivel = nivel;
    }
}
