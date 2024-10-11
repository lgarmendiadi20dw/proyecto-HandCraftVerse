package com.proyeto.hand_craft_verse.dominio;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="Producto")
public class Producto {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Vendedor vendedor;

    private String nombre;

    private float precio;

    private int stock;

    private String descripcion;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios;

    @ManyToMany(mappedBy = "productosFavoritos", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comprador> compradoresFavoritos;

    @ManyToMany(mappedBy = "productos", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Colore> colores;

    @OneToMany
    (mappedBy="producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Multimedia> multimedias;
}
