package com.proyeto.hand_craft_verse.dto;

import com.proyeto.hand_craft_verse.dominio.direccion.TipoDireccion;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DireccionDTO {
    private int id;
    private String pais;
    private String provincia;
    private String municipio;
    private String calle;
    private int prefijo;
    private int numTelefono;
    private String destinatario;
    private TipoDireccion tipoDireccion;
}
