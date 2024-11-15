package com.proyeto.hand_craft_verse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

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
    private String password;
    private int telefono;
    private int numVentas;
    private String descripcion;
    private List<Integer> direccionesEnvio;

   
}
