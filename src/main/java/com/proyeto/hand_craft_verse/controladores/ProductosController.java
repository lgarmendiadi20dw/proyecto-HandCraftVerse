package com.proyeto.hand_craft_verse.controladores;

import com.proyeto.hand_craft_verse.aplicacion.IAplicacion;
import com.proyeto.hand_craft_verse.dominio.productos.Producto;
import com.proyeto.hand_craft_verse.dominio.usuarios.Vendedor;

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
    public ResponseEntity<Producto> addProduct(@RequestBody Producto producto) {

        try {
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
