package com.proyeto.hand_craft_verse.controladores;

import com.proyeto.hand_craft_verse.aplicacion.AplicacionUsuario;
import com.proyeto.hand_craft_verse.aplicacion.IAplicacion;
import com.proyeto.hand_craft_verse.dominio.productos.Categoria;
import com.proyeto.hand_craft_verse.dominio.productos.Colore;
import com.proyeto.hand_craft_verse.dominio.productos.Multimedia;
import com.proyeto.hand_craft_verse.dominio.productos.Producto;
import com.proyeto.hand_craft_verse.dominio.usuarios.Vendedor;
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
    AplicacionUsuario aplicacionUsuario;

    @Autowired
    IAplicacion<Multimedia> aplicacionMultimedia;

    @Autowired
    IAplicacion<Colore> aplicacionColore;

    @Autowired
    IAplicacion<Categoria> aplicacionCategoria;

    /**
     * Método para obtener la información de un producto específico.
     * @param id El ID del producto a buscar.
     * @return Un objeto ProductoDTO con la información del producto.
     */
    @GetMapping("/{id}")
    public ProductoDTO viewProduct(@PathVariable int id) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO = DtoConverter.fromProducto(aplicacionProducto.buscar(id));
        return productoDTO;
    }

    /**
     * Método para eliminar un producto por su ID.
     * @param id El ID del producto a eliminar.
     * @return Una respuesta HTTP indicando el resultado de la operación.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable int id) {
        if (aplicacionProducto.eliminar(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Método para crear un nuevo producto.
     * @param productoDTO El DTO del producto a crear.
     * @return Una respuesta HTTP con el producto creado o un error.
     */
    @PostMapping("/create")
    public ResponseEntity<Producto> addProduct(@RequestBody ProductoDTO productoDTO) {
        try {
            Producto producto = DtoConverter.fromProductoDTO(productoDTO);
            Vendedor vendedor = aplicacionVendedor.buscar(productoDTO.getVendedorId());
            List<Colore> colores = new ArrayList<>();
            for (String coloreDTO : productoDTO.getColores()) {
                colores.add(aplicacionColore.buscar(coloreDTO));
            }

            List<Categoria> categorias = new ArrayList<>();
            for (String categoriaDTO : productoDTO.getCategorias()) {
                categorias.add(aplicacionCategoria.buscar(categoriaDTO));
            }

            producto.setColores(colores);
            producto.setCategorias(categorias);
            if (vendedor == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            producto.setVendedor(vendedor);

            if (aplicacionProducto.guardar(producto)) {
                return ResponseEntity.status(HttpStatus.CREATED).body(producto);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Método para actualizar un producto existente.
     * @param id El ID del producto a actualizar.
     * @param producto El objeto Producto con los nuevos datos.
     * @return Una respuesta HTTP indicando el resultado de la operación.
     */
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

    /**
     * Método para obtener una lista de todos los productos.
     * @return Una lista de objetos ProductoDTO con la información de todos los productos.
     */
    @GetMapping("/all")
    public List<ProductoDTO> verProductoesList() {
        List<ProductoDTO> productosDto = new ArrayList<>();
        for (Producto producto : aplicacionProducto.obtenerTodos()) {
            productosDto.add(DtoConverter.fromProducto(producto));
        }
        return productosDto;
    }
}