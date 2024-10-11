package com.proyeto.hand_craft_verse.dominio;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Direccion") 
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usa IDENTITY si usas bases de datos autoincrementales
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

    @Enumerated(EnumType.STRING)
    private TipoDireccion tipoDireccion; // Este debe ser compatible con la base de datos
}