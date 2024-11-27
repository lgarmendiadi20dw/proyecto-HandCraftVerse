package com.proyeto.hand_craft_verse.controladores;

import com.proyeto.hand_craft_verse.aplicacion.AplicacionUsuario;
import com.proyeto.hand_craft_verse.aplicacion.IAplicacion;
import com.proyeto.hand_craft_verse.dominio.productos.Categoria;
import com.proyeto.hand_craft_verse.dominio.productos.Colore;
import com.proyeto.hand_craft_verse.dominio.productos.Multimedia;
import com.proyeto.hand_craft_verse.dominio.productos.Producto;
import com.proyeto.hand_craft_verse.dominio.usuarios.Vendedor;
import com.proyeto.hand_craft_verse.dto.Converter.DtoConverter;
import com.proyeto.hand_craft_verse.dto.Converter.ProductoDtoConverter;
import com.proyeto.hand_craft_verse.dto.Productos.MostrarProductoDTO;
import com.proyeto.hand_craft_verse.dto.Productos.MultimediaDTO;
import com.proyeto.hand_craft_verse.dto.Productos.ProductoDTO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
     * 
     * @param id El ID del producto a buscar.
     * @return Un objeto ProductoDTO con la información del producto.
     */
    @GetMapping("/{id}")
    public MostrarProductoDTO viewProduct(@PathVariable int id) {
        MostrarProductoDTO productoDTO = new MostrarProductoDTO();
        productoDTO = ProductoDtoConverter.fromProductoToMostrarProductoDTO(aplicacionProducto.buscar(id));
        return productoDTO;
    }

    /**
     * Método para eliminar un producto por su ID.
     * 
     * @param id El ID del producto a eliminar.
     * @return Una respuesta HTTP indicando el resultado de la operación.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable int id) {
        Producto producto = aplicacionProducto.buscar(id);
        if (producto != null) {
            producto.getCategorias().clear(); // Eliminar asociaciones de categorías
            aplicacionProducto.actualizar(producto); // Actualizar el producto antes de eliminar
            if (aplicacionProducto.eliminar(id)) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Método para crear un nuevo producto.
     * 
     * @param productoDTO El DTO del producto a crear.
     * @return Una respuesta HTTP con el producto creado o un error.
     */
   @PostMapping("/create")
//    @PreAuthorize("hasRole('VENDEDOR')")
    public ResponseEntity<Producto> addProduct(@RequestBody ProductoDTO productoDTO) {
        // public ResponseEntity<Producto> addProduct(@RequestBody ProductoDTO productoDTO, @AuthenticationPrincipal Vendedor user) {
        try {

            Producto prueba = ProductoDtoConverter.fromProductoDTO(productoDTO);
            Vendedor vendedor = aplicacionVendedor.buscar(productoDTO.getVendedorId());
            List<Colore> colores = new ArrayList<>();
            for (String coloreDTO : productoDTO.getColores()) {
                colores.add(aplicacionColore.buscar(coloreDTO));
            }

            List<Categoria> categorias = new ArrayList<>();
            for (String categoriaDTO : productoDTO.getCategorias()) {
                categorias.add(aplicacionCategoria.buscar(categoriaDTO));
            }
            
            
            prueba.setColores(colores);
            prueba.setCategorias(categorias);
            if (vendedor == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Vendedor no encontrado
            }

            prueba.setVendedor(vendedor); // Establecer el vendedor encontrado

            if (aplicacionProducto.guardar(prueba)) {
                return ResponseEntity.status(HttpStatus.CREATED).body(prueba);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Método para actualizar un producto existente.
     * 
     * @param id       El ID del producto a actualizar.
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
     * 
     * @return Una lista de objetos ProductoDTO con la información de todos los
     *         productos.
     */
    @GetMapping("/all")
    public List<ProductoDTO> verProductoesList() {
        List<ProductoDTO> productosDto = new ArrayList<>();
        for (Producto producto : aplicacionProducto.obtenerTodos()) {
            productosDto.add(ProductoDtoConverter.fromProducto(producto));
        }
        return productosDto;
    }

    /**
     * Método para obtener productos por categoría.
     * 
     * @param nombre El nombre de la categoría.
     * @return Una lista de objetos ProductoDTO con la información de los productos
     *         de la categoría.
     */
    @GetMapping("/categoria/{nombre}")
    public List<MostrarProductoDTO> getProductsByCategory(@PathVariable String nombre) {
        List<MostrarProductoDTO> productosDto = new ArrayList<>();

        List<Producto> productos = aplicacionProducto.obtenerPorColeccion("categorias", "nombre", nombre);

        for (Producto producto : productos) {
            productosDto.add(ProductoDtoConverter.fromProductoToMostrarProductoDTO(producto));
        }

        return productosDto;
    }

    @GetMapping("/{id}/multimedia")
    public List<MultimediaDTO> getProductMultimedia(@PathVariable int id) {
        
        List<MultimediaDTO> multimediaDTO= new ArrayList<>();
        List<Multimedia> multimediaList = aplicacionMultimedia.obtenerPorColeccion("producto", "id", id);
        for (Multimedia multimedia : multimediaList) {
            
            multimediaDTO.add(DtoConverter.fromMultimedia(multimedia));
        }
        return multimediaDTO;
    }

   
}