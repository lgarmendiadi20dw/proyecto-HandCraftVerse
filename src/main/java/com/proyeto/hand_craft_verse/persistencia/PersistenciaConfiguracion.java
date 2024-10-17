package com.proyeto.hand_craft_verse.persistencia;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;

import com.proyeto.hand_craft_verse.dominio.Admin;
import com.proyeto.hand_craft_verse.dominio.Categoria;
import com.proyeto.hand_craft_verse.dominio.Colore;
import com.proyeto.hand_craft_verse.dominio.Comentario;
import com.proyeto.hand_craft_verse.dominio.Comprador;
import com.proyeto.hand_craft_verse.dominio.Direccion;
import com.proyeto.hand_craft_verse.dominio.EstadoPedido;
import com.proyeto.hand_craft_verse.dominio.Multimedia;
import com.proyeto.hand_craft_verse.dominio.Pedido;
import com.proyeto.hand_craft_verse.dominio.PedidoProducto;
import com.proyeto.hand_craft_verse.dominio.Producto;
import com.proyeto.hand_craft_verse.dominio.RedesSociales;
import com.proyeto.hand_craft_verse.dominio.TipoDireccion;
import com.proyeto.hand_craft_verse.dominio.Usuario;
import com.proyeto.hand_craft_verse.dominio.Vendedor;

@org.springframework.context.annotation.Configuration
public class PersistenciaConfiguracion {
    @Bean
    public IPersistencia<Usuario> getPersistenciaUsuario() {

        return new Persistencia<Usuario>(getSession(), Usuario.class);
    }
    @Bean
    public IPersistencia<Comprador> getPersistenciaComprador() {

        return new Persistencia<Comprador>(getSession(), Comprador.class);
    }

    @Bean
    public Session getSession() {
        Configuration conf = new Configuration();
        SessionFactory factory = conf.configure()
                .addAnnotatedClass(Usuario.class)
                .addAnnotatedClass(Vendedor.class)
                .addAnnotatedClass(Admin.class)
                .addAnnotatedClass(Comprador.class)
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
        System.out.println("hola1");

        Session session = factory.openSession();

        System.out.println("hola");
        return session;
    }
}
