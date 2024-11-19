package com.proyeto.hand_craft_verse.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.proyeto.hand_craft_verse.aplicacion.AplicacionUsuario;

@Configuration
public class UserDetailsConf {

    @Bean
    public UserDetailsService customUserDetailsService(AplicacionUsuario repositorioUsuario) {
        return new CustomUserDetailsService(repositorioUsuario);
    }
    
}