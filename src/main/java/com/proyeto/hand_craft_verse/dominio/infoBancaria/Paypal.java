package com.proyeto.hand_craft_verse.dominio.infoBancaria;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.Entity;


@Data
@NoArgsConstructor
@Entity
@SuperBuilder
public class Paypal extends InfoBancaria{
    
    private String email;
    private String password;

    

}
