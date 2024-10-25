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
public class ProductoDTO {
    private int vendedorId; // Cambiado de Vendedor a vendedorId
    private String nombre;
    private float precio;
    private int stock;
    
    private String descripcion;
    private List<ColoreDTO> colores; // O puedes usar una lista de ColorDTO
    private List<MultimediaDTO> multimedia; // O puedes usar una lista de MultimediaDTO
    private List<CategoriaDTO> categorias; // O puedes usar una lista de CategoriaDTO
}
