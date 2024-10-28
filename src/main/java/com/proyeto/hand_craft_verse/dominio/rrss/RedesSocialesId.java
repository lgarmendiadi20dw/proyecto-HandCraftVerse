package com.proyeto.hand_craft_verse.dominio.rrss;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RedesSocialesId {

    private String username;

    private String plataforma;
}
