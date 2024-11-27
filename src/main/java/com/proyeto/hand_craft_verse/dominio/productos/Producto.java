package com.proyeto.hand_craft_verse.dominio.productos;

import java.util.List;

import com.proyeto.hand_craft_verse.dominio.usuarios.Usuario;
import com.proyeto.hand_craft_verse.dominio.usuarios.Vendedor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Vendedor vendedor;

    private String nombre;

    private float precio;

    private int stock;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @JsonIgnore
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios;

    @JsonIgnore
    @ManyToMany(mappedBy = "productosFavoritos", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<Usuario> usuariosFavoritos;

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinTable(name = "color_producto", joinColumns = @JoinColumn(name = "producto"), inverseJoinColumns = @JoinColumn(name = "color"))
    private List<Colore> colores;

    @JsonIgnore
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Multimedia> multimedias;

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "producto_categoria", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "producto_id"), // Clave foránea de la entidad 'Producto'
            inverseJoinColumns = @JoinColumn(name = "categoria_nombre") // Clave foránea de la entidad 'Categoria'
    )
    private List<Categoria> categorias;

}
