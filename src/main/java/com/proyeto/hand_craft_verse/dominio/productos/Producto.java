package com.proyeto.hand_craft_verse.dominio.productos;

import java.util.List;

import com.proyeto.hand_craft_verse.dominio.usuarios.Usuario;
import com.proyeto.hand_craft_verse.dominio.usuarios.Vendedor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    @JsonBackReference
    private Vendedor vendedor;

    private String nombre;

    private float precio;

    private int stock;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comentario> comentarios;

    @ManyToMany(mappedBy = "productosFavoritos", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<Usuario> usuariosFavoritos;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinTable(name = "color_producto", joinColumns = @JoinColumn(name = "producto"), inverseJoinColumns = @JoinColumn(name = "color"))
    @JsonIgnoreProperties("productos")  // Evitar la serialización de la relación circular
    private List<Colore> colores;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Multimedia> multimedias;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "producto_categoria", 
               joinColumns = @JoinColumn(name = "producto_id"), 
               inverseJoinColumns = @JoinColumn(name = "categoria_nombre"))
    @JsonIgnoreProperties("productos")  // Evitar la serialización de la relación circular
    private List<Categoria> categorias;

    // Si deseas, puedes inicializar colecciones en el constructor o en un método.
    public void initializeCollections() {
        if (this.categorias != null) {
            this.categorias.size();  // Inicializar la colección de categorías
        }
        if (this.colores != null) {
            this.colores.size();  // Inicializar la colección de colores
        }
    }
}