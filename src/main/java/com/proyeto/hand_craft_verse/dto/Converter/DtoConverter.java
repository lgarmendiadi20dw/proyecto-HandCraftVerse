package com.proyeto.hand_craft_verse.dto.Converter;

import com.proyeto.hand_craft_verse.aplicacion.Aplicacion;
import com.proyeto.hand_craft_verse.dominio.productos.Categoria;
import com.proyeto.hand_craft_verse.dominio.productos.Colore;
import com.proyeto.hand_craft_verse.dominio.productos.Multimedia;
import com.proyeto.hand_craft_verse.dominio.productos.Producto;
import com.proyeto.hand_craft_verse.dominio.usuarios.Vendedor;
import com.proyeto.hand_craft_verse.dto.CategoriaDTO;
import com.proyeto.hand_craft_verse.dto.ColoreDTO;
import com.proyeto.hand_craft_verse.dto.MultimediaDTO;
import com.proyeto.hand_craft_verse.dto.ProductoDTO;
import com.proyeto.hand_craft_verse.dto.VendedorDTO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DtoConverter {
    @Autowired
    private static Aplicacion<Colore> aplicacionColore;

    @Autowired
    private static Aplicacion<Categoria> aplicacionCategoria;

    
        public static VendedorDTO fromVendedor(Vendedor vendedor) {
            return VendedorDTO.builder()
                    .dni(vendedor.getDni())
                    .nombreUsuario(vendedor.getUsername())
                    .nombre(vendedor.getNombre())
                    .apellidos(vendedor.getApellidos())
                    .email(vendedor.getEmail())
                    .imagen(vendedor.getImagen())
                    .password(vendedor.getPassword())
                    .telefono(vendedor.getTelefono())
                    .numVentas(vendedor.getNum_ventas())
                    .descripcion(vendedor.getDescripcion())
                    .build();
        }
    
        public static Vendedor fromVendedorDTO(VendedorDTO vendedorDTO) {
            Vendedor vendedor = new Vendedor();
            vendedor.setUsername(vendedorDTO.getNombreUsuario());
            vendedor.setEmail(vendedorDTO.getEmail());
            vendedor.setPassword(vendedorDTO.getPassword());
            vendedor.setTelefono(vendedorDTO.getTelefono());
            vendedor.setNum_ventas(vendedorDTO.getNumVentas());
            vendedor.setNombre(vendedorDTO.getNombre());
            vendedor.setApellidos(vendedorDTO.getApellidos());
            vendedor.setDescripcion(vendedorDTO.getDescripcion());
            vendedor.setImagen(vendedorDTO.getImagen());
            return vendedor;
        }
    
        public static ColoreDTO fromColore(Colore colore) {
            return ColoreDTO.builder()
                    .hex(colore.getHex())
                    .nombre(colore.getNombre())
                    .build();
        }
    
        public static Colore fromColoreDTO(ColoreDTO coloreDTO) {
            Colore colore = new Colore();
            colore.setHex(coloreDTO.getHex());
            colore.setNombre(coloreDTO.getNombre());
            return colore;
        }
    
        public static CategoriaDTO fromCategoria(Categoria categoria) {
            return CategoriaDTO.builder()
                    .nombre(categoria.getNombre())
                    .descripcion(categoria.getDescripcion())
                    .build();
        }
    
        public static Categoria fromCategoriaDTO(CategoriaDTO categoriaDTO) {
            Categoria categoria = new Categoria();
            categoria.setNombre(categoriaDTO.getNombre());
            categoria.setDescripcion(categoriaDTO.getDescripcion());
            return categoria;
        }
    
        public static MultimediaDTO fromMultimedia(Multimedia multimedia) {
            return MultimediaDTO.builder()
                    .url(multimedia.getUrl())
                    .alt(multimedia.getAlt())
                    .nombreArchivo(multimedia.getNombreArchivo())
                    .build();
        }
    
        public static Multimedia fromMultimediaDTO(MultimediaDTO multimediaDTO) {
            Multimedia multimedia = new Multimedia();
            multimedia.setUrl(multimediaDTO.getUrl());
            multimedia.setAlt(multimediaDTO.getAlt());
            multimedia.setNombreArchivo(multimediaDTO.getNombreArchivo());
            return multimedia;
        }
    
        public static ProductoDTO fromProducto(Producto producto) {
            ProductoDTO productoDTO = ProductoDTO.builder()
                    .vendedorId(producto.getVendedor().getId())
                    .nombre(producto.getNombre())
                    .precio(producto.getPrecio())
                    .stock(producto.getStock())
                    .descripcion(producto.getDescripcion())
                    .colores(new ArrayList<>())
                    .multimedia(new ArrayList<>())
                    .categorias(new ArrayList<>())
                    .build();
    
            for (Colore colore : producto.getColores()) {
                productoDTO.getColores().add(colore.getNombre());
            }
    
            for (Multimedia multimedia : producto.getMultimedias()) {
                productoDTO.getMultimedia().add(fromMultimedia(multimedia));
            }
    
            for (Categoria categoria : producto.getCategorias()) {
                productoDTO.getCategorias().add(categoria.getNombre());
            }
    
            return productoDTO;
        }
    
        public static Producto fromProductoDTO(ProductoDTO productoDTO) {
            Producto producto = new Producto();
            
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setDescripcion(productoDTO.getDescripcion());

        
        if (productoDTO.getMultimedia() != null) {
            List<Multimedia> multimedias = new ArrayList<>();
            for (MultimediaDTO multimediaDTO : productoDTO.getMultimedia()) {
                multimedias.add(fromMultimediaDTO(multimediaDTO));
            }
            producto.setMultimedias(multimedias);
        } else {
            producto.setMultimedias(null);
        }

        return producto;
    }
}
