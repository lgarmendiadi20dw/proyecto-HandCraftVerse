package com.proyeto.hand_craft_verse.dto;

import com.proyeto.hand_craft_verse.dominio.productos.Categoria;
import com.proyeto.hand_craft_verse.dominio.productos.Colore;
import com.proyeto.hand_craft_verse.dominio.productos.Multimedia;
import com.proyeto.hand_craft_verse.dominio.productos.Producto;
import com.proyeto.hand_craft_verse.dominio.usuarios.Vendedor;

import java.util.List;
import java.util.stream.Collectors;

public class DtoConverter {

    public static VendedorDTO fromVendedor(Vendedor vendedor) {
        VendedorDTO toReturn = VendedorDTO.builder()
                .dni(vendedor.getDni())
                .nombreUsuario(vendedor.getNombre_usuario())
                .nombre(vendedor.getNombre())
                .apellidos(vendedor.getApellidos())
                .email(vendedor.getEmail())
                .imagen(vendedor.getImagen())
                .contrasena(vendedor.getContrasena())
                .telefono(vendedor.getTelefono())
                .numVentas(vendedor.getNum_ventas())
                .descripcion(vendedor.getDescripcion())
                .build();

        return toReturn;
    }

    public static Vendedor fromVendedorDTO(VendedorDTO vendedorDTO) {
        Vendedor vendedor = new Vendedor(
                vendedorDTO.getNombreUsuario(),
                vendedorDTO.getEmail(),
                vendedorDTO.getContrasena(),
                vendedorDTO.getTelefono(),
                vendedorDTO.getDescripcion()
        );

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

    
    public static ProductoDTO fromProducto(Producto producto) {
        return ProductoDTO.builder()
                .vendedor(producto.getVendedor()) // Ejemplo de uso de un campo específico
                .nombre(producto.getNombre())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .descripcion(producto.getDescripcion())
                .colores(producto.getColores().stream().map(Colore::getHex).collect(Collectors.toList())) // Suponiendo que deseas el código HEX
                .multimedia(producto.getMultimedias().stream().map(Multimedia::getUrl).collect(Collectors.toList())) // O el atributo que utilices
                .categorias(producto.getCategorias().stream().map(Categoria::getNombre).collect(Collectors.toList())) // Suponiendo que deseas el nombre de la categoría
                .build();
    }

    public static Producto fromProductoDTO(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setVendedor(productoDTO.getVendedor());
        // Debes establecer el vendedor según tu lógica de negocio, aquí se omite para simplicidad
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setDescripcion(productoDTO.getDescripcion());
        // Debes establecer los colores, multimedias y categorías según tu lógica de negocio
        return producto;
    }
}
