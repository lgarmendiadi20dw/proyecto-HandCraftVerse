package com.proyeto.hand_craft_verse.dominio.usuarios;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyeto.hand_craft_verse.dominio.direccion.Direccion;
import com.proyeto.hand_craft_verse.dominio.pedidos.Pedido;
import com.proyeto.hand_craft_verse.dominio.productos.Comentario;
import com.proyeto.hand_craft_verse.dominio.productos.Producto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true, nullable = false)
    private String dni;

    @Column(unique = true, nullable = false)
    private String nombre_usuario;

    private String nombre;

    private String apellidos;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(name = "contrasena", nullable = false, length = 60)
    private String contrasena;

    private String imagen;

    @Column(unique = true)
    private int telefono;

    @OneToMany(mappedBy = "cuentaUsuario", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Direccion> direccionesEnvio;

    @OneToMany(mappedBy = "usuario")
    private List<Pedido> pedidos;

    @OneToMany(mappedBy = "usuario")
    private List<Comentario> comentarios;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinTable(name = "favoritos", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "producto_id"))
    private List<Producto> productosFavoritos;


    public Usuario(String dni, String nombre_usuario, String nombre, String apellidos, String email,
            String contrasena, int telefono) {
        this.dni = dni;
        this.nombre_usuario = nombre_usuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.contrasena = contrasena;
        this.telefono = telefono;
    }

    public Usuario(String nombre_usuario, String email,
            String contrasena, int telefono) {
        this.nombre_usuario = nombre_usuario;
        this.email = email;
        this.contrasena = contrasena;
        this.telefono = telefono;
    }
}
