package com.proyeto.hand_craft_verse.dominio.infoBancaria;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.proyeto.hand_craft_verse.dominio.usuarios.Usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "InfoBancaria")
public class InfoBancaria {
    @Id
    private int id;

    @ManyToMany(mappedBy = "infoBancariaList", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("infoBancariaList")
    private List<Usuario> usuarios;
}
