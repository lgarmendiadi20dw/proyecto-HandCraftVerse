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
    private int vendedorId;
    private String nombre;
    private float precio;
    private int stock;
    
    private String descripcion;
    private List<String> colores;
    private List<MultimediaDTO> multimedia;
    private List<String> categorias;
}
