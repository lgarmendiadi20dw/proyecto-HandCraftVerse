package com.proyeto.hand_craft_verse.dto;

import com.proyeto.hand_craft_verse.dominio.productos.Categoria;
import com.proyeto.hand_craft_verse.dominio.productos.Colore;
import com.proyeto.hand_craft_verse.dominio.productos.Multimedia;
import com.proyeto.hand_craft_verse.dominio.productos.Producto;
import com.proyeto.hand_craft_verse.dominio.usuarios.Vendedor;

import java.util.ArrayList;
import java.util.List;

public class DtoConverter {

    public static VendedorDTO fromVendedor(Vendedor vendedor) {
        return VendedorDTO.builder()
                .dni(vendedor.getDni())
                .nombreUsuario(vendedor.getUsername())
                .nombre(vendedor.getNombre())
                .apellidos(vendedor.getApellidos())
                .email(vendedor.getEmail())
                .imagen(vendedor.getImagen())
                .contrasena(vendedor.getPassword())
                .telefono(vendedor.getTelefono())
                .numVentas(vendedor.getNum_ventas())
                .descripcion(vendedor.getDescripcion())
                .build();
    }

    public static Vendedor fromVendedorDTO(VendedorDTO vendedorDTO) {
        Vendedor vendedor = new Vendedor(
                vendedorDTO.getNombreUsuario(),
                vendedorDTO.getEmail(),
                vendedorDTO.getContrasena(),
                vendedorDTO.getTelefono(),
                vendedorDTO.getDescripcion());

        vendedor.setNum_ventas(vendedorDTO.getNumVentas());
        vendedor.setNombre(vendedorDTO.getNombre());
        vendedor.setApellidos(vendedorDTO.getApellidos());
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
                .vendedorId(producto.getVendedor().getId()) // Obtener el ID del vendedor
                .nombre(producto.getNombre())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .descripcion(producto.getDescripcion())
                .colores(new ArrayList<>()) // Inicializar la lista de colores
                .multimedia(new ArrayList<>()) // Inicializar la lista de multimedia
                .categorias(new ArrayList<>()) // Inicializar la lista de categorías
                .build();

        // Convertir la lista de Colores a ColoreDTO
        for (Colore colore : producto.getColores()) {
            productoDTO.getColores().add(DtoConverter.fromColore(colore));
        }

        // Convertir la lista de Multimedia a MultimediaDTO
        for (Multimedia multimedia : producto.getMultimedias()) {
            productoDTO.getMultimedia().add(DtoConverter.fromMultimedia(multimedia));
        }

        // Convertir la lista de Categorías a CategoriaDTO
        for (Categoria categoria : producto.getCategorias()) {
            productoDTO.getCategorias().add(DtoConverter.fromCategoria(categoria));
        }

        return productoDTO;
    }

    public static Producto fromProductoDTO(ProductoDTO productoDTO) {
        Producto producto = new Producto();

        // Aquí se asume que se buscará el vendedor por ID antes de asignarlo
        // Vendedor vendedor =
        // aplicacionVendedor.buscarPorId(productoDTO.getVendedorId());
        // producto.setVendedor(vendedor); // Establecer el vendedor

        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setDescripcion(productoDTO.getDescripcion());

        // Convertir la lista de ColoreDTO a Colore
        // List<Colore> colores = new ArrayList<>();
        // for (ColoreDTO coloreDTO : productoDTO.getColores()) {
        // colores.add(DtoConverter.fromColoreDTO(coloreDTO));
        // }
        // producto.setColores(colores);

        // // Convertir la lista de MultimediaDTO a Multimedia
        List<Multimedia> multimedias = new ArrayList<>();
        for (MultimediaDTO multimediaDTO : productoDTO.getMultimedia()) {
            multimedias.add(DtoConverter.fromMultimediaDTO(multimediaDTO));
        }
        producto.setMultimedias(multimedias);

        // // Convertir la lista de CategoriaDTO a Categoria
        // List<Categoria> categorias = new ArrayList<>();
        // for (CategoriaDTO categoriaDTO : productoDTO.getCategorias()) {
        // categorias.add(DtoConverter.fromCategoriaDTO(categoriaDTO));
        // }
        // producto.setCategorias(categorias);

        return producto;
    }

}
