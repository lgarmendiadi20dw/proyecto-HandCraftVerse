package com.proyeto.hand_craft_verse.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.proyeto.hand_craft_verse.aplicacion.AplicacionUsuario;
import com.proyeto.hand_craft_verse.dominio.usuarios.Usuario;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AplicacionUsuario aplicacionUsuario;

   

    @Override
    public Usuario loadUserByUsername(String username) throws UsernameNotFoundException {
        return aplicacionUsuario.buscarPorNombre(username);
        
    }
}