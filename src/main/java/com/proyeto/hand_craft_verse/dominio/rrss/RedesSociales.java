package com.proyeto.hand_craft_verse.dominio.rrss;

import com.proyeto.hand_craft_verse.dominio.usuarios.Vendedor;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="RedesSociales")
public class RedesSociales {

    @EmbeddedId
    private RedesSocialesId rrssId;

    // Relación ManyToOne con Vendedor
    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Vendedor vendedor;

}
