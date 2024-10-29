package com.proyeto.hand_craft_verse.dominio.usuarios;

import java.util.List;

import com.proyeto.hand_craft_verse.dominio.productos.Producto;
import com.proyeto.hand_craft_verse.dominio.rrss.RedesSociales;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@Entity
public class Vendedor extends Usuario{

    
    private int num_ventas;
    
    private String descripcion;

    @OneToMany(mappedBy = "vendedor")
    private List<RedesSociales> redes_sociales;

    @OneToMany(mappedBy = "vendedor")
    private List<Producto> productos;
    
    

    
    // public Vendedor(String dni,String username,String nombre,String apellidos,String email,String password, int telefono, String descripcion){
    //     super( dni, username, nombre, apellidos, email, password, telefono);
    //     this.num_ventas=0;
    //     this.descripcion=descripcion;
    // }

    // public Vendedor(String username,String email,String password, int telefono, String descripcion){
    //     super( username, email, password, telefono);
    //     this.num_ventas=0;
    //     this.descripcion=descripcion;
    // }

    
}
