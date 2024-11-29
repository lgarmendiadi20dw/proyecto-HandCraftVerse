package com.proyeto.hand_craft_verse.dominio.productos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyeto.hand_craft_verse.dominio.usuarios.Usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="Comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "producto") 
    private Producto producto;

    @JsonBackReference
    @ManyToOne  
    @JoinColumn(name = "usuario", nullable = false)
    private Usuario usuario;

    private String texto;

    private int valoracion;
}
