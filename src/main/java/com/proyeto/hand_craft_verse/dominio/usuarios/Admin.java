package com.proyeto.hand_craft_verse.dominio.usuarios;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@Entity
@SuperBuilder
public class Admin extends Usuario {
    private int nivel;
}
