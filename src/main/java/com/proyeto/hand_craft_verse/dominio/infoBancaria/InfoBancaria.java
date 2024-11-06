package com.proyeto.hand_craft_verse.dominio.infoBancaria;

import java.util.List;

import com.proyeto.hand_craft_verse.dominio.usuarios.Usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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

    @ManyToMany(mappedBy = "infoBancariaList")
    private List<Usuario> usuarios;
}
