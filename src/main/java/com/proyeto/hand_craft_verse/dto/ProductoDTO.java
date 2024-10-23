package com.proyeto.hand_craft_verse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.proyeto.hand_craft_verse.dominio.usuarios.Vendedor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {
    private Vendedor vendedor; 
    private String nombre;
    private float precio;
    private int stock;
    private String descripcion;
    private List<String> colores; // O puedes usar una lista de ColorDTO
    private List<String> multimedia; // O puedes usar una lista de MultimediaDTO
    private List<String> categorias; // O puedes usar una lista de CategoriaDTO
    
}
