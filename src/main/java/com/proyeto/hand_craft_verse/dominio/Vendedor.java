package com.proyeto.hand_craft_verse.dominio;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@Entity
public class Vendedor extends Usuario{

    
    private int num_ventas;
    
    private String descripcion;

    // @OneToMany(mappedBy = "vendedor")
    // private List<RedesSociales> redes_sociales;

    @OneToMany(mappedBy = "vendedor")
    private List<Producto> productos;
    
    

    
    public Vendedor(String dni,String nombre_usuario,String nombre,String apellidos,String email,String contrasena, int telefono, String descripcion){
        super( dni, nombre_usuario, nombre, apellidos, email, contrasena, telefono);
        this.num_ventas=0;
        this.descripcion=descripcion;
    }

    
}
