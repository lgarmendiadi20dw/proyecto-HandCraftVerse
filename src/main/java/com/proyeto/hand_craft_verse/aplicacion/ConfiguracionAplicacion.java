package com.proyeto.hand_craft_verse.aplicacion;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.proyeto.hand_craft_verse.dominio.direccion.Direccion;
import com.proyeto.hand_craft_verse.dominio.pedidos.Pedido;
import com.proyeto.hand_craft_verse.dominio.pedidos.PedidoProducto;
import com.proyeto.hand_craft_verse.dominio.productos.Categoria;
import com.proyeto.hand_craft_verse.dominio.productos.Colore;
import com.proyeto.hand_craft_verse.dominio.productos.Comentario;
import com.proyeto.hand_craft_verse.dominio.productos.Multimedia;
import com.proyeto.hand_craft_verse.dominio.productos.Producto;
import com.proyeto.hand_craft_verse.dominio.rrss.RedesSociales;
import com.proyeto.hand_craft_verse.dominio.usuarios.Admin;
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

    @Bean
    public IAplicacion<Admin> getAplicacionAdmines(IPersistencia<Admin> persistenciaAdmin)
    {
        return new Aplicacion<Admin>(persistenciaAdmin);
    }

    @Bean
    public IAplicacion<RedesSociales> getAplicacionRedesSociales(IPersistencia<RedesSociales> persistenciaRedesSociales)
    {
        return new Aplicacion<RedesSociales>(persistenciaRedesSociales);
    }

    @Bean
    public IAplicacion<Producto> getAplicacionProducto(IPersistencia<Producto> persistenciaProducto)
    {
        return new Aplicacion<Producto>(persistenciaProducto);
    }

    @Bean
    public IAplicacion<Multimedia> getAplicacionMultimedia(IPersistencia<Multimedia> persistenciaMultimedia)
    {
        return new Aplicacion<Multimedia>(persistenciaMultimedia);
    }

    @Bean
    public IAplicacion<Colore> getAplicacionColore(IPersistencia<Colore> persistenciaColore)
    {
        return new Aplicacion<Colore>(persistenciaColore);
    }

    @Bean
    public IAplicacion<Comentario> getAplicacionComentario(IPersistencia<Comentario> persistenciaComentario)
    {
        return new Aplicacion<Comentario>(persistenciaComentario);
    }

    @Bean
    public IAplicacion<Categoria> getAplicacionCategoria(IPersistencia<Categoria> persistenciaCategoria)
    {
        return new Aplicacion<Categoria>(persistenciaCategoria);
    }

    @Bean
    public IAplicacion<Pedido> getAplicacionPedido(IPersistencia<Pedido> persistenciaPedido)
    {
        return new Aplicacion<Pedido>(persistenciaPedido);
    }

    @Bean
    public IAplicacion<PedidoProducto> getAplicacionPedidoProducto(IPersistencia<PedidoProducto> persistenciaPedidoProducto)
    {
        return new Aplicacion<PedidoProducto>(persistenciaPedidoProducto);
    }

    @Bean
    public IAplicacion<Direccion> getAplicacionDireccion(IPersistencia<Direccion> persistenciaDireccion)
    {
        return new Aplicacion<Direccion>(persistenciaDireccion);
    }
    
}
