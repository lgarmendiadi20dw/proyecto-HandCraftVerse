package com.proyeto.hand_craft_verse.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Direccion")
public class Direccion {
    @Id
    @GeneratedValue
    private int id;

    private String pais;

    private String provincia;

    private String municipio;

    private String calle;
    
}
