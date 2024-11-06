package com.proyeto.hand_craft_verse.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.proyeto.hand_craft_verse.aplicacion.AplicacionUsuario;
import com.proyeto.hand_craft_verse.dominio.usuarios.Usuario;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private AplicacionUsuario persistencia;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario user = persistencia.buscarPorNombre(username);
        
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return user;
        

    }

}