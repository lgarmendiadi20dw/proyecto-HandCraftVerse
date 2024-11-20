package com.proyeto.hand_craft_verse.dto.Converter;

import java.util.ArrayList;
import java.util.List;

import com.proyeto.hand_craft_verse.dominio.productos.Categoria;
import com.proyeto.hand_craft_verse.dominio.productos.Colore;
import com.proyeto.hand_craft_verse.dominio.productos.Multimedia;
import com.proyeto.hand_craft_verse.dominio.productos.Producto;
import com.proyeto.hand_craft_verse.dto.Productos.MostrarProductoDTO;
import com.proyeto.hand_craft_verse.dto.Productos.MultimediaDTO;
import com.proyeto.hand_craft_verse.dto.Productos.ProductoDTO;

public class ProductoDtoConverter {
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
                productoDTO.getMultimedia().add(DtoConverter.fromMultimedia(multimedia));
            }
    
            for (Categoria categoria : producto.getCategorias()) {
                productoDTO.getCategorias().add(categoria.getNombre());
            }
    
            return productoDTO;
        }

        public static MostrarProductoDTO fromProductoToMostrarProductoDTO(Producto producto) {
            MostrarProductoDTO mostrarProductoDTO = MostrarProductoDTO.builder()
                    .id(producto.getId())
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
                mostrarProductoDTO.getColores().add(colore.getNombre());
            }
    
            for (Multimedia multimedia : producto.getMultimedias()) {
                mostrarProductoDTO.getMultimedia().add(DtoConverter.fromMultimedia(multimedia));
            }
    
            for (Categoria categoria : producto.getCategorias()) {
                mostrarProductoDTO.getCategorias().add(categoria.getNombre());
            }
    
            return mostrarProductoDTO;
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
                Multimedia multimedia = DtoConverter.fromMultimediaDTO(multimediaDTO);
                multimedia.setProducto(producto);
                multimedias.add(multimedia);
            }
            producto.setMultimedias(multimedias);
        } else {
            producto.setMultimedias(null);
        }

        return producto;
    }
}
