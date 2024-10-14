package com.proyeto.hand_craft_verse.dominio;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Pedido")
public class Pedido {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_comprador")
    private Comprador comprador;

    private Date fechaCompra;

    private String numeroSeguimiento;

    @Enumerated
    private EstadoPedido estado;

    @ManyToOne
    @JoinColumn(name="direccion")
    private Direccion direccion;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoProducto> pedidoProductos;

    private float costeTotal;
}
