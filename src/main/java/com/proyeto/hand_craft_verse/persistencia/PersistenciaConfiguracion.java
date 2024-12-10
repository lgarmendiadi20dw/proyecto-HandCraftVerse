package com.proyeto.hand_craft_verse.persistencia;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;

import com.proyeto.hand_craft_verse.dominio.Dibujo;
import com.proyeto.hand_craft_verse.dominio.direccion.Direccion;
import com.proyeto.hand_craft_verse.dominio.direccion.TipoDireccion;
import com.proyeto.hand_craft_verse.dominio.infoBancaria.InfoBancaria;
import com.proyeto.hand_craft_verse.dominio.infoBancaria.Paypal;
import com.proyeto.hand_craft_verse.dominio.infoBancaria.Tarjeta;
import com.proyeto.hand_craft_verse.dominio.pedidos.EstadoPedido;
import com.proyeto.hand_craft_verse.dominio.pedidos.Pedido;
import com.proyeto.hand_craft_verse.dominio.pedidos.PedidoProducto;
import com.proyeto.hand_craft_verse.dominio.productos.Categoria;
import com.proyeto.hand_craft_verse.dominio.productos.Colore;
import com.proyeto.hand_craft_verse.dominio.productos.Comentario;
import com.proyeto.hand_craft_verse.dominio.productos.Multimedia;
import com.proyeto.hand_craft_verse.dominio.productos.Producto;
import com.proyeto.hand_craft_verse.dominio.rrss.RedesSociales;
import com.proyeto.hand_craft_verse.dominio.usuarios.Admin;
import com.proyeto.hand_craft_verse.dominio.usuarios.UserRoles;
import com.proyeto.hand_craft_verse.dominio.usuarios.Usuario;
import com.proyeto.hand_craft_verse.dominio.usuarios.Vendedor;

@org.springframework.context.annotation.Configuration
public class PersistenciaConfiguracion {

    @Bean
    public IPersistencia<Usuario> getPersistenciaUsuario(SessionFactory session) {
        return new Persistencia<Usuario>(session, Usuario.class);
    }
    @Bean
    public IPersistencia<Dibujo> getPersistenciaDibujo(SessionFactory session) {
        return new Persistencia<Dibujo>(session, Dibujo.class);
    }

    @Bean
    public IPersistencia<Vendedor> getPersistenciaVendedor(SessionFactory session) {
        return new Persistencia<Vendedor>(session, Vendedor.class);
    }

    @Bean
    public IPersistencia<Admin> getPersistenciaAdmin(SessionFactory session) {
        return new Persistencia<Admin>(session, Admin.class);
    }

    @Bean
    public IPersistencia<Producto> getPersistenciaProducto(SessionFactory session) {
        return new Persistencia<Producto>(session, Producto.class);
    }

    @Bean
    public IPersistencia<Multimedia> getPersistenciaMultimedia(SessionFactory session) {
        return new Persistencia<Multimedia>(session, Multimedia.class);
    }

    @Bean
    public IPersistencia<Colore> getPersistenciaColore(SessionFactory session) {
        return new Persistencia<Colore>(session, Colore.class);
    }

    @Bean
    public IPersistencia<Comentario> getPersistenciaComentario(SessionFactory session) {
        return new Persistencia<Comentario>(session, Comentario.class);
    }

    @Bean
    public IPersistencia<Direccion> getPersistenciaDireccion(SessionFactory session) {
        return new Persistencia<Direccion>(session, Direccion.class);
    }

    @Bean
    public IPersistencia<EstadoPedido> getPersistenciaEstadoPedido(SessionFactory session) {
        return new Persistencia<EstadoPedido>(session, EstadoPedido.class);
    }

    @Bean
    public IPersistencia<Pedido> getPersistenciaPedido(SessionFactory session) {
        return new Persistencia<Pedido>(session, Pedido.class);
    }

    @Bean
    public IPersistencia<PedidoProducto> getPersistenciaPedidoProducto(SessionFactory session) {
        return new Persistencia<PedidoProducto>(session, PedidoProducto.class);
    }

    @Bean
    public IPersistencia<RedesSociales> getPersistenciaRedesSociales(SessionFactory session) {
        return new Persistencia<RedesSociales>(session, RedesSociales.class);
    }

    @Bean
    public IPersistencia<TipoDireccion> getPersistenciaTipoDireccion(SessionFactory session) {
        return new Persistencia<TipoDireccion>(session, TipoDireccion.class);
    }

    @Bean
    public IPersistencia<Categoria> getPersistenciaCategoria(SessionFactory session) {
        return new Persistencia<Categoria>(session, Categoria.class);
    }

    @Bean
    public SessionFactory getSession() {
        Configuration conf = new Configuration();
        SessionFactory factory = conf.configure()
                .addAnnotatedClass(Usuario.class)
                .addAnnotatedClass(Vendedor.class)
                .addAnnotatedClass(Admin.class)
                .addAnnotatedClass(UserRoles.class)
                .addAnnotatedClass(TipoDireccion.class)
                .addAnnotatedClass(EstadoPedido.class)
                .addAnnotatedClass(Producto.class)
                .addAnnotatedClass(PedidoProducto.class)
                .addAnnotatedClass(Pedido.class)
                .addAnnotatedClass(Comentario.class)
                .addAnnotatedClass(RedesSociales.class)
                .addAnnotatedClass(Multimedia.class)
                .addAnnotatedClass(Colore.class)
                .addAnnotatedClass(Categoria.class)
                .addAnnotatedClass(Direccion.class)
                .addAnnotatedClass(InfoBancaria.class)
                .addAnnotatedClass(Tarjeta.class)
                .addAnnotatedClass(Paypal.class)
                .addAnnotatedClass(Dibujo.class)
                .buildSessionFactory();

        return factory;
    }
}