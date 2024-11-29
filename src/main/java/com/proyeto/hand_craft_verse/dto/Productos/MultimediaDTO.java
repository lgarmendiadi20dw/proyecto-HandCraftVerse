package com.proyeto.hand_craft_verse.dto.Productos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MultimediaDTO {
    private String url;             // URL de la multimedia
    private String alt;             // Texto alternativo de la imagen
    private String nombreArchivo;    // Nombre del archivo de la multimedia
    private int producto;           // ID del producto al que pertenece la multimedia
}
