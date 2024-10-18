package com.proyeto.hand_craft_verse.aplicacion;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.proyeto.hand_craft_verse.dominio.usuarios.Comprador;
import com.proyeto.hand_craft_verse.dominio.usuarios.Usuario;
import com.proyeto.hand_craft_verse.dominio.usuarios.Vendedor;
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
    @Bean
    public IAplicacion<Vendedor> getAplicacionVendedores(IPersistencia<Vendedor> persistenciaVendedor)
    {
        return new Aplicacion<Vendedor>(persistenciaVendedor);
    }

   
    
}
