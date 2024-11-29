package com.proyeto.hand_craft_verse.dominio.pedidos;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="pedido_producto")
public class PedidoProducto {

    @EmbeddedId
    private PedidoProductoId id;
    
    @JsonBackReference
    @ManyToOne
    @MapsId("pedido")
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;  

    private int cantidad;
}

