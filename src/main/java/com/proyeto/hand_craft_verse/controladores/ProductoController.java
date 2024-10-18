package com.proyeto.hand_craft_verse.controladores;

import com.proyeto.hand_craft_verse.dominio.productos.Producto;
import com.proyeto.hand_craft_verse.aplicacion.IAplicacion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final IAplicacion<Producto> aplicacionProducto;

    public ProductoController(IAplicacion<Producto> aplicacionProducto) {
        this.aplicacionProducto = aplicacionProducto;
    }

    // GET all productos
    @GetMapping("/all")
    public ResponseEntity<List<Producto>> getAllProductos() {
        List<Producto> productos = aplicacionProducto.obtenerTodos();
        if (productos.isEmpty()) {
            return new ResponseEntity.status(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity.status(productos, HttpStatus.OK);
    }

    // GET producto by ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable int id) {
        Producto producto = aplicacionProducto.buscar(id);
        if (producto == null) {
            return new ResponseEntity.status(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity.status(producto, HttpStatus.OK);
    }

    // CREATE a new producto
    @PostMapping("/create")
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        if (aplicacionProducto.guardar(producto)) {
            return new ResponseEntity.status(producto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
    }

    // UPDATE producto by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateProducto(@PathVariable int id, @RequestBody Producto producto) {
        producto.setId(id);
        Producto updatedProducto = aplicacionProducto.actualizar(producto);
        if (updatedProducto == null) {
            return new ResponseEntity.status(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity.status(HttpStatus.NO_CONTENT);
    }

    // DELETE producto by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable int id) {
        if (aplicacionProducto.eliminar(id)) {
            return new ResponseEntity.status(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity.status(HttpStatus.NOT_FOUND);
        }
    }
}
