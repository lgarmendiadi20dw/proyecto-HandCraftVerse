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

    @PutMapping("/update/{nombre}")
    public ResponseEntity<Void> updateCategoria(@PathVariable String nombre, @RequestBody Categoria categoria) {
        Categoria existingCategoria = aplicacionCategoria.buscar(nombre);
        if (existingCategoria != null) {
            existingCategoria.setDescripcion(categoria.getDescripcion());
            // No se puede actualizar el nombre, ya que es la clave primaria

            if (aplicacionCategoria.actualizar(existingCategoria) != null) {
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
