package com.proyeto.hand_craft_verse.dominio;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Comprador extends Usuario {

    @OneToMany(mappedBy = "cuentaComprador", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Direccion> direccionesEnvio;

    @OneToMany(mappedBy = "comprador")
    private List<Pedido> pedidos;

    @OneToMany(mappedBy = "comprador")
    private List<Comentario> comentarios;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinTable(name = "favoritos", joinColumns = @JoinColumn(name = "comprador_id"), inverseJoinColumns = @JoinColumn(name = "producto_id"))
    private List<Producto> productosFavoritos;

    // Constructor con parámetros
    public Comprador(String dni, String nombre_usuario, String nombre, String apellidos, String email,
            String contrasena, int telefono) {
        super(dni, nombre_usuario, nombre, apellidos, email, contrasena, telefono);
        this.direccionesEnvio=null;
        this.comentarios=null;
        this.pedidos=null;
        this.productosFavoritos=null;
    }
}
