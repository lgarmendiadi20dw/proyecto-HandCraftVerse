package com.proyeto.hand_craft_verse.dominio;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Admin extends Usuario{
    private int nivel;
}
