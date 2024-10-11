package com.proyeto.hand_craft_verse.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="Comentarios")
public class Comentario {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "producto") 
    private Producto producto;

    @ManyToOne  
    @JoinColumn(name = "comprador", nullable = false)
    private Comprador comprador;

    private String texto;

    private int valoracion;
}
