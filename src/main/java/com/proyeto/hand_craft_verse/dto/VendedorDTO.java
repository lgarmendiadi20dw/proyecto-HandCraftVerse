package com.proyeto.hand_craft_verse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendedorDTO  {
    private String dni;
    private String nombreUsuario;
    private String nombre;
    private String apellidos;
    private String email;
    private String imagen;
    private String contrasena;
    private int telefono;
    private int numVentas;
    private String descripcion;

   
}
