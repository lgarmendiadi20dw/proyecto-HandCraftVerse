package com.proyeto.hand_craft_verse.controladores;

import com.proyeto.hand_craft_verse.aplicacion.IAplicacion;
import com.proyeto.hand_craft_verse.dominio.productos.Categoria;
import com.proyeto.hand_craft_verse.dominio.productos.Colore;
import com.proyeto.hand_craft_verse.dominio.productos.Multimedia;
import com.proyeto.hand_craft_verse.dominio.productos.Producto;
import com.proyeto.hand_craft_verse.dominio.usuarios.Vendedor;
import com.proyeto.hand_craft_verse.dto.CategoriaDTO;
import com.proyeto.hand_craft_verse.dto.ColoreDTO;
import com.proyeto.hand_craft_verse.dto.MultimediaDTO;
import com.proyeto.hand_craft_verse.dto.ProductoDTO;
import com.proyeto.hand_craft_verse.dto.Converter.DtoConverter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productos")
public class ProductosController {

    @Autowired
    IAplicacion<Producto> aplicacionProducto;

    @Autowired
    IAplicacion<Vendedor> aplicacionVendedor;

    @Autowired
    IAplicacion<Multimedia> aplicacionMultimedia;

    @Autowired
    IAplicacion<Colore> aplicacionColore;

    @Autowired
    IAplicacion<Categoria> aplicacionCategoria;

    @GetMapping("/{id}")
    public Producto viewProduct(@PathVariable int id) {
        return aplicacionProducto.buscar(id);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable int id) {
        if (aplicacionProducto.eliminar(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Producto> addProduct(@RequestBody ProductoDTO productoDTO) {
        try {

            Producto producto = DtoConverter.fromProductoDTO(productoDTO);
            // Aquí se asume que tienes un método para buscar un Vendedor por ID
            Vendedor vendedor = aplicacionVendedor.buscar(productoDTO.getVendedorId());
            List<Colore> colores = new ArrayList<>();
            for (ColoreDTO coloreDTO : productoDTO.getColores()) {
                colores.add(aplicacionColore.buscar(coloreDTO.getHex()));
            }

            List<Categoria> categorias = new ArrayList<>();
            for (CategoriaDTO categoriaDTO : productoDTO.getCategorias()) {
                categorias.add(aplicacionCategoria.buscar(categoriaDTO.getNombre()));
            }
            
            
            producto.setColores(colores);
            producto.setCategorias(categorias);
            if (vendedor == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Vendedor no encontrado
            }

            // Convertir ProductoDTO a Producto
            producto.setVendedor(vendedor); // Establecer el vendedor encontrado

            if (aplicacionProducto.guardar(producto)) {
                return ResponseEntity.status(HttpStatus.CREATED).body(producto);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable int id, @RequestBody Producto producto) {
        Producto existingProduct = aplicacionProducto.buscar(id);
        if (existingProduct != null) {
            existingProduct.setNombre(producto.getNombre());
            existingProduct.setPrecio(producto.getPrecio());
            existingProduct.setStock(producto.getStock());
            existingProduct.setDescripcion(producto.getDescripcion());
            existingProduct.setColores(producto.getColores());
            existingProduct.setMultimedias(producto.getMultimedias());
            existingProduct.setCategorias(producto.getCategorias());

            if (aplicacionProducto.actualizar(existingProduct) != null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    public List<Producto> verProductoesList() {
        System.out.println(aplicacionProducto.obtenerTodos());
        return aplicacionProducto.obtenerTodos();
    }
}
