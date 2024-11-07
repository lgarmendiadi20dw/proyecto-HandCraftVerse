package com.proyeto.hand_craft_verse.dominio.productos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Color")
public class Colore {
    @Id
    private String hex;

    private String nombre;

    
}
