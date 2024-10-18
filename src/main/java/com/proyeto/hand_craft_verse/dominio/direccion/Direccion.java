package com.proyeto.hand_craft_verse.dominio.direccion;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyeto.hand_craft_verse.dominio.pedidos.Pedido;
import com.proyeto.hand_craft_verse.dominio.usuarios.Comprador;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Direccion") 
public class Direccion {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "comprador_id") // Cambiar a "comprador_id" para hacer más claro
    @JsonBackReference
    private Comprador cuentaComprador;

    private String pais;
    private String provincia;
    private String municipio;
    private String calle;
    private int prefijo;
    private int numTelefono; 
    private String destinatario;

    @OneToMany(mappedBy = "direccion")
    private List<Pedido> pedidos;

    @Enumerated(EnumType.STRING)
    private TipoDireccion tipoDireccion; 
}