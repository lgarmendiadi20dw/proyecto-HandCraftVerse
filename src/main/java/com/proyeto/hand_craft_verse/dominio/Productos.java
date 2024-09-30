package com.proyeto.hand_craft_verse.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="Productos")
public class Productos {
    @Id
    @GeneratedValue
    private int id;

    private String nombre;

    private float precio;

    private int stock;

    private String descripcion;

    private boolean personalizable;

}
