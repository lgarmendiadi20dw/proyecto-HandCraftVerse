package com.proyeto.hand_craft_verse.dominio.direccion;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyeto.hand_craft_verse.dominio.pedidos.Pedido;
import com.proyeto.hand_craft_verse.dominio.usuarios.Usuario;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Direccion") 
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "usuario_id") 
    @JsonBackReference
    private Usuario cuentaUsuario;

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