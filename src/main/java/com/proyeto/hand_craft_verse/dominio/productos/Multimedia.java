package com.proyeto.hand_craft_verse.dominio.productos;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Multimedia")
@Builder
public class Multimedia {
    
    @Id
    private String url;//URL en la que se almacena la imagen

    private String alt;

    private String nombreArchivo; // dato que se utiliza para mostrar la imagen en el front

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="producto")
    private Producto  producto;

   
}
