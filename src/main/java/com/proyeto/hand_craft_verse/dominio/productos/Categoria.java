package com.proyeto.hand_craft_verse.dominio.productos;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Categoria {
    @Id
    private String nombre;

    private String descripcion;

    @ManyToMany(mappedBy = "categorias", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("categorias")
    private List<Producto> productos;
}
