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
import com.proyeto.hand_craft_verse.dto.Productos.MultimediaDTO;
import com.proyeto.hand_craft_verse.dto.Productos.ProductoDTO;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
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
     * 
     * @param id El ID del producto a buscar.
     * @return Un objeto ProductoDTO con la información del producto.
     */
    @GetMapping("/{id}")
    public ProductoDTO viewProduct(@PathVariable int id) {
        Producto producto = aplicacionProducto.buscar(id);
        if (producto != null) {
            Hibernate.initialize(producto.getColores());
            Hibernate.initialize(producto.getCategorias());
            Hibernate.initialize(producto.getMultimedias());
            Hibernate.initialize(producto.getUsuariosFavoritos()); // Inicializar la colección usuariosFavoritos
            return ProductoDtoConverter.fromProducto(producto);
        }
        return null;
    }

    /**
     * Método para eliminar un producto por su ID.
     * 
     * @param id El ID del producto a eliminar.
     * @return Una respuesta HTTP indicando el resultado de la operación.
     */

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Void> deleteProductById(@PathVariable int id) {
        Producto producto = aplicacionProducto.buscar(id);
        if (producto != null) {
            // Inicializar la colección 'categorias' para evitar LazyInitializationException
            Hibernate.initialize(producto.getCategorias());

            // Eliminar asociaciones de categorías si existen
            if (producto.getCategorias() != null) {
                producto.getCategorias().clear();
            }

            // Eliminar el producto
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
    // @PreAuthorize("hasRole('VENDEDOR')")
    // public ResponseEntity<Producto> addProduct(@RequestBody ProductoDTO
    // productoDTO, @AuthenticationPrincipal Vendedor user) {
    public ResponseEntity<Producto> addProduct(@RequestBody ProductoDTO productoDTO) {

        try {

            Producto producto = ProductoDtoConverter.fromProductoDTO(productoDTO);
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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Vendedor no encontrado
            }

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

    /**
     * Método para actualizar un producto existente.
     * 
     * @param id       El ID del producto a actualizar.
     * @param producto El objeto Producto con los nuevos datos.
     * @return Una respuesta HTTP indicando el resultado de la operación.
     */
    @PutMapping("/update/{id}")
    @Transactional
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody ProductoDTO productoDTO) {
        try {
            Producto existingProduct = aplicacionProducto.buscar(id);

            existingProduct.setNombre(productoDTO.getNombre());
            existingProduct.setDescripcion(productoDTO.getDescripcion());
            existingProduct.setPrecio(productoDTO.getPrecio());
            existingProduct.setStock(productoDTO.getStock());
        
            List<Multimedia> multimediaList = new ArrayList<>();
            for (MultimediaDTO multimediaDTO : productoDTO.getMultimedia()) {
                multimediaList.add(aplicacionMultimedia.buscar(multimediaDTO.getUrl()));
            }

            List<Colore> colores = new ArrayList<>();
            for (String coloreDTO : productoDTO.getColores()) {
                colores.add(aplicacionColore.buscar(coloreDTO));
            }

            List<Categoria> categorias = new ArrayList<>();
            for (String categoriaDTO : productoDTO.getCategorias()) {
                categorias.add(aplicacionCategoria.buscar(categoriaDTO));
            }
            existingProduct.setColores(colores);
            existingProduct.setCategorias(categorias);
            existingProduct.setMultimedias(multimediaList);

            if (aplicacionProducto.guardar(existingProduct) ) {
                // return ResponseEntity.status(HttpStatus.CREATED).body(existingProduct);
            return ResponseEntity.ok(existingProduct);

            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

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
            Hibernate.initialize(producto.getUsuariosFavoritos()); // Inicializar la colección usuariosFavoritos
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
    public List<ProductoDTO> getProductsByCategory(@PathVariable String nombre) {
        List<ProductoDTO> productosDto = new ArrayList<>();
        List<Producto> productos = aplicacionProducto.obtenerPorColeccion("categorias", "nombre", nombre);
        for (Producto producto : productos) {
            Hibernate.initialize(producto.getUsuariosFavoritos()); // Inicializar la colección usuariosFavoritos
            productosDto.add(ProductoDtoConverter.fromProducto(producto));
        }
        return productosDto;
    }

    

    @GetMapping("/{id}/multimedia")
    public List<MultimediaDTO> getProductMultimedia(@PathVariable int id) {

        List<MultimediaDTO> multimediaDTO = new ArrayList<>();
        List<Multimedia> multimediaList = aplicacionMultimedia.obtenerPorColeccion("producto", "id", id);
        for (Multimedia multimedia : multimediaList) {

            multimediaDTO.add(DtoConverter.fromMultimedia(multimedia));
        }
        return multimediaDTO;
    }

    /**
 * Método para buscar productos por un campo específico.
 * 
 * @param campo El campo por el que se va a hacer la búsqueda (por ejemplo, 'nombre').
 * @param query El valor a buscar.
 * @return Una lista de productos que coinciden con el término de búsqueda.
 */
@GetMapping("/buscar")
public List<ProductoDTO> searchProducts(@RequestParam String campo, @RequestParam String query) {
    List<ProductoDTO> productosDto = new ArrayList<>();
    List<Producto> productos = aplicacionProducto.buscarPorCampo(campo, query);
    for (Producto producto : productos) {
        Hibernate.initialize(producto.getUsuariosFavoritos()); // Inicializar la colección usuariosFavoritos
        productosDto.add(ProductoDtoConverter.fromProducto(producto));
    }
    return productosDto;
}

}