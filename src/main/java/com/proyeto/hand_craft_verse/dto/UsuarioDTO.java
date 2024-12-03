package com.proyeto.hand_craft_verse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private int id;
    private String dni;
    private String username;
    private String nombre;
    private String apellidos;
    private String email;
    private String imagen;
    private String password;
    private int telefono;
    private String roles;



 
}
