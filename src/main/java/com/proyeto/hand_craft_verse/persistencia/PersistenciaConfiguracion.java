package com.proyeto.hand_craft_verse.persistencia;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;

import com.proyeto.hand_craft_verse.dominio.direccion.Direccion;
import com.proyeto.hand_craft_verse.dominio.direccion.TipoDireccion;
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
// import com.proyeto.hand_craft_verse.dominio.usuarios.Comprador;
import com.proyeto.hand_craft_verse.dominio.usuarios.Usuario;
import com.proyeto.hand_craft_verse.dominio.usuarios.Vendedor;

@org.springframework.context.annotation.Configuration
public class PersistenciaConfiguracion {
    @Bean
    public IPersistencia<Usuario> getPersistenciaUsuario() {

        return new Persistencia<Usuario>(getSession(), Usuario.class);
    }

    // @Bean
    // public IPersistencia<Comprador> getPersistenciaComprador() {

    //     return new Persistencia<Comprador>(getSession(), Comprador.class);
    // }

    @Bean
    public IPersistencia<Vendedor> getPersistenciaVendedor() {

        return new Persistencia<Vendedor>(getSession(), Vendedor.class);
    }

    @Bean
    public IPersistencia<Admin> getPersistenciaAdmin() {

        return new Persistencia<Admin>(getSession(), Admin.class);
    }

    @Bean
    public IPersistencia<Producto> getPersistenciaProducto() {

        return new Persistencia<Producto>(getSession(), Producto.class);
    }

    @Bean
    public IPersistencia<Multimedia> getPersistenciaMultimedia() {

        return new Persistencia<Multimedia>(getSession(), Multimedia.class);
    }

    @Bean
    public IPersistencia<Colore> getPersistenciaColore() {

        return new Persistencia<Colore>(getSession(), Colore.class);
    }

    @Bean
    public IPersistencia<Comentario> getPersistenciaComentario() {

        return new Persistencia<Comentario>(getSession(), Comentario.class);
    }

    @Bean
    public IPersistencia<Direccion> getPersistenciaDireccion() {

        return new Persistencia<Direccion>(getSession(), Direccion.class);
    }

    @Bean
    public IPersistencia<EstadoPedido> getPersistenciaEstadoPedido() {

        return new Persistencia<EstadoPedido>(getSession(), EstadoPedido.class);
    }

    @Bean
    public IPersistencia<Pedido> getPersistenciaPedido() {

        return new Persistencia<Pedido>(getSession(), Pedido.class);
    }

    @Bean
    public IPersistencia<PedidoProducto> getPersistenciaPedidoProducto() {

        return new Persistencia<PedidoProducto>(getSession(), PedidoProducto.class);
    }

    @Bean
    public IPersistencia<RedesSociales> getPersistenciaRedesSociales() {

        return new Persistencia<RedesSociales>(getSession(), RedesSociales.class);
    }

    @Bean
    public IPersistencia<TipoDireccion> getPersistenciaTipoDireccion() {

        return new Persistencia<TipoDireccion>(getSession(), TipoDireccion.class);
    }

    @Bean
    public IPersistencia<Categoria> getPersistenciaCategoria() {

        return new Persistencia<Categoria>(getSession(), Categoria.class);
    }

    @Bean
    public Session getSession() {
        Configuration conf = new Configuration();
        SessionFactory factory = conf.configure()
                .addAnnotatedClass(Usuario.class)
                .addAnnotatedClass(Vendedor.class)
                .addAnnotatedClass(Admin.class)
                // .addAnnotatedClass(Comprador.class)
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

                .buildSessionFactory();

        Session session = factory.openSession();

        return session;
    }
}
