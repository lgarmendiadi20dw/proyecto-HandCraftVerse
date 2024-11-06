package com.proyeto.hand_craft_verse.dominio.infoBancaria;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@Entity
@SuperBuilder
public class Tarjeta extends InfoBancaria {
    
    private String numero;
    private String fechaVencimiento;
    private int cvv;
    private String nombreTitular;

}
