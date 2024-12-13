package com.proyeto.hand_craft_verse.controladores;

import com.proyeto.hand_craft_verse.aplicacion.IAplicacion;
import com.proyeto.hand_craft_verse.dominio.productos.Categoria;
import com.proyeto.hand_craft_verse.dto.CategoriaDTO;
import com.proyeto.hand_craft_verse.dto.Converter.DtoConverter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorias")
public class CategoriasController {

    @Autowired
    IAplicacion<Categoria> aplicacionCategoria;

    @GetMapping("/{nombre}")
    public Categoria viewCategoria(@PathVariable String nombre) {
        return aplicacionCategoria.buscar(nombre);

    }

    @DeleteMapping("/delete/{nombre}")
    public ResponseEntity<Void> deleteCategoriaByNombre(@PathVariable String nombre) {
        if (aplicacionCategoria.eliminar(nombre)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Categoria> addCategoria(@RequestBody CategoriaDTO categoriaDto) {
        try {
            Categoria categoria = DtoConverter.fromCategoriaDTO(categoriaDto);
            if (aplicacionCategoria.guardar(categoria)) {
                return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @PostMapping("/createAllDefault")
public ResponseEntity<List<Categoria>> addDefaultCategorias() {
    try {
        // Lista de categorías por defecto
        List<CategoriaDTO> categoriasDto = List.of(
            CategoriaDTO.builder()
                .nombre("Decoración para el hogar")
                .descripcion("Accesorios y piezas únicas para embellecer tu hogar, como cuadros, cerámica y lámparas artesanales.")
                .build(),
            CategoriaDTO.builder()
                .nombre("Joyería y accesorios")
                .descripcion("Collares, pulseras, anillos y otros accesorios hechos a mano con materiales únicos.")
                .build(),
            CategoriaDTO.builder()
                .nombre("Arte y pintura")
                .descripcion("Obras de arte originales como cuadros, ilustraciones y esculturas hechas a mano.")
                .build(),
            CategoriaDTO.builder()
                .nombre("Muebles y carpintería")
                .descripcion("Mesas, sillas, estanterías y otros muebles diseñados con un toque artesanal.")
                .build(),
            CategoriaDTO.builder()
                .nombre("Juguetes y juegos")
                .descripcion("Juguetes educativos y juegos hechos a mano, ideales para niños y adultos.")
                .build(),
            CategoriaDTO.builder()
                .nombre("Cerámica y vidrio")
                .descripcion("Vajillas, jarrones y artículos decorativos hechos de cerámica o vidrio soplado.")
                .build()
        );

        // Convertimos las categorías DTO en entidades y las guardamos
        List<Categoria> categorias = categoriasDto.stream()
                .map(DtoConverter::fromCategoriaDTO)
                .filter(aplicacionCategoria::guardar) // Guardar y filtrar las que se guardan con éxito
                .toList();

        if (!categorias.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(categorias);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}


    @PutMapping("/update/{nombre}")
    public ResponseEntity<Void> updateCategoria(@PathVariable String nombre, @RequestBody Categoria categoria) {
        Categoria existingCategoria = aplicacionCategoria.buscar(nombre);
        if (existingCategoria != null) {
            existingCategoria.setDescripcion(categoria.getDescripcion());
            // No se puede actualizar el nombre, ya que es la clave primaria

            if (aplicacionCategoria.actualizar(existingCategoria)) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    public List<CategoriaDTO> verCategoriasList() {
        List<CategoriaDTO> respuesta = new ArrayList<>();
        List<Categoria> categorias = aplicacionCategoria.obtenerTodos();

        try {
            for (Categoria categoria : categorias) {
                respuesta.add(DtoConverter.fromCategoria(categoria));
            }

        } catch (Exception e) {

        }

        return respuesta;
    }
}
