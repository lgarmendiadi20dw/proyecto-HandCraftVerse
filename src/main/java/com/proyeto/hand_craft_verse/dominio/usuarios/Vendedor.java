package com.proyeto.hand_craft_verse.dominio.usuarios;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyeto.hand_craft_verse.dominio.productos.Producto;
import com.proyeto.hand_craft_verse.dominio.rrss.RedesSociales;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
public class Vendedor extends Usuario{

    
    private int num_ventas;
    
    private String descripcion;

    @JsonManagedReference
    @OneToMany(mappedBy = "vendedor", fetch = FetchType.EAGER)
    private List<RedesSociales> redes_sociales;

    @JsonManagedReference
    @OneToMany(mappedBy = "vendedor", fetch = FetchType.EAGER)
    private List<Producto> productos;
    
    


    
}
