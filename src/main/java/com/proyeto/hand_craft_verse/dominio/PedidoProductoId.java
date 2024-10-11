package com.proyeto.hand_craft_verse.dominio;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PedidoProductoId {
    @ManyToOne
    private Pedido pedido;
    
    @ManyToOne
    private Producto producto;
}

