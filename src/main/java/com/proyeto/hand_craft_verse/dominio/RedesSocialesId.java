package com.proyeto.hand_craft_verse.dominio;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RedesSocialesId {

    private String nombre_usuario;

    private String plataforma;
}
