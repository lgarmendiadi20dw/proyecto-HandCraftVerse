package com.proyeto.hand_craft_verse.dominio;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Comprador extends Usuario {
    
    @OneToMany(mappedBy = "comprador")
    private List<Direccion> direccionesEnvio;
}
