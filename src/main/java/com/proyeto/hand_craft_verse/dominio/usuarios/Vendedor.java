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
    
    

    
    public Vendedor(String dni,String nombre_usuario,String nombre,String apellidos,String email,String contrasena, int telefono, String descripcion){
        super( dni, nombre_usuario, nombre, apellidos, email, contrasena, telefono);
        this.num_ventas=0;
        this.descripcion=descripcion;
    }

    public Vendedor(String nombre_usuario,String email,String contrasena, int telefono, String descripcion){
        super( nombre_usuario, email, contrasena, telefono);
        this.num_ventas=0;
        this.descripcion=descripcion;
    }

    
}
