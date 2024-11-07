package com.proyeto.hand_craft_verse.dominio.productos;

import java.util.List;

import com.proyeto.hand_craft_verse.dominio.usuarios.Usuario;
import com.proyeto.hand_craft_verse.dominio.usuarios.Vendedor;

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

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios;

    @ManyToMany(mappedBy = "productosFavoritos", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<Usuario> usuariosFavoritos;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinTable(name = "color_producto", joinColumns = @JoinColumn(name = "producto"), inverseJoinColumns = @JoinColumn(name = "color"))
    private List<Colore> colores;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Multimedia> multimedias;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinTable(name = "producto_categoria", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "producto_id"), // Clave foránea de la entidad 'Producto'
            inverseJoinColumns = @JoinColumn(name = "categoria_nombre") // Clave foránea de la entidad 'Categoria'
    )
    private List<Categoria> categorias;

    public Producto(Vendedor vendedor, String nombre, float precio, int stock, String descripcion, List<Colore> colores,
            List<Multimedia> multimedias, List<Categoria> categorias) {
        this.vendedor = vendedor;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.descripcion = descripcion;
        this.colores = colores;
        this.multimedias = multimedias;
        this.categorias = categorias;
    }

    public Producto(Vendedor vendedor, String nombre, float precio, int stock, String descripcion, List<Colore> colores,
            List<Categoria> categorias) {
        this.vendedor = vendedor;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.colores = colores;
        this.categorias = categorias;
    }

}
