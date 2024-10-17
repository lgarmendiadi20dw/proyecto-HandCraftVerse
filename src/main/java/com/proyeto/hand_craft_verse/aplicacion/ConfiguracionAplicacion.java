package com.proyeto.hand_craft_verse.aplicacion;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.proyeto.hand_craft_verse.dominio.Comprador;
import com.proyeto.hand_craft_verse.dominio.Usuario;
import com.proyeto.hand_craft_verse.persistencia.IPersistencia;


@Configuration
public class ConfiguracionAplicacion {

    @Bean
    public IAplicacion<Usuario> getAplicacionUsuarios(IPersistencia<Usuario> persistenciaUsuario)
    {
        return new Aplicacion<Usuario>(persistenciaUsuario);
    }
    @Bean
    public IAplicacion<Comprador> getAplicacionCompradores(IPersistencia<Comprador> persistenciaComprador)
    {
        return new Aplicacion<Comprador>(persistenciaComprador);
    }

   
    
}
