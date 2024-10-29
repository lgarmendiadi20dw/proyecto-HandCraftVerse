package com.proyeto.hand_craft_verse.dominio.usuarios;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyeto.hand_craft_verse.dominio.direccion.Direccion;
import com.proyeto.hand_craft_verse.dominio.pedidos.Pedido;
import com.proyeto.hand_craft_verse.dominio.productos.Comentario;
import com.proyeto.hand_craft_verse.dominio.productos.Producto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "Usuario")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<UserRoles> roles;

    @Column(unique = true, nullable = true)
    private String dni;

    @Column(unique = true, nullable = false)
    private String username;

    private String nombre;
    private String apellidos;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    private String imagen;

    @Column(nullable = true)
    private int telefono;

    @OneToMany(mappedBy = "cuentaUsuario", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Direccion> direccionesEnvio;

    @OneToMany(mappedBy = "usuario")
    private List<Pedido> pedidos;

    @OneToMany(mappedBy = "usuario")
    private List<Comentario> comentarios;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinTable(name = "favoritos", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "producto_id"))
    private List<Producto> productosFavoritos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.toString()))
                .collect(Collectors.toSet());
    }
}
