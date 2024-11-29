package com.proyeto.hand_craft_verse.controladores;

import com.proyeto.hand_craft_verse.aplicacion.IAplicacion;
import com.proyeto.hand_craft_verse.dominio.productos.Multimedia;
import com.proyeto.hand_craft_verse.dominio.productos.Producto;
import com.proyeto.hand_craft_verse.dto.Converter.DtoConverter;
import com.proyeto.hand_craft_verse.dto.Productos.MultimediaDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/multimedia")
public class MultimediaController {

    @Autowired
    IAplicacion<Multimedia> aplicacionMultimedia;

    @Autowired
    IAplicacion<Producto> aplicacionProducto;


//guardar imagenes
// direcion+nombre basado en idproducto+idimagen

    @GetMapping("/{url}")
    public ResponseEntity<Multimedia> viewMultimedia(@PathVariable String url) {
        Multimedia multimedia = aplicacionMultimedia.buscar(url);
        if (multimedia != null) {
            return ResponseEntity.ok(multimedia);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{url}")
    public ResponseEntity<Void> deleteMultimediaByUrl(@PathVariable String url) {
        if (aplicacionMultimedia.eliminar(url)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Transactional
@PostMapping("/create")
public ResponseEntity<Multimedia> addMultimedia(@RequestBody MultimediaDTO multimediaDto) {
    Multimedia multimedia = DtoConverter.fromMultimediaDTO(multimediaDto);
    multimedia.setProducto(aplicacionProducto.buscar(multimediaDto.getProducto()));
    try {
        if (aplicacionMultimedia.guardar(multimedia)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(multimedia);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}


    @PutMapping("/update/{url}")
    public ResponseEntity<Void> updateMultimedia(@PathVariable String url, @RequestBody Multimedia multimedia) {
        Multimedia existingMultimedia = aplicacionMultimedia.buscar(url);
        if (existingMultimedia != null) {
            existingMultimedia.setAlt(multimedia.getAlt());
            existingMultimedia.setNombreArchivo(multimedia.getNombreArchivo());
            existingMultimedia.setProducto(multimedia.getProducto());

            if (aplicacionMultimedia.actualizar(existingMultimedia) != null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<Multimedia>> getMultimediaByProducto(@PathVariable int productoId) {
        List<Multimedia> multimediaList = aplicacionMultimedia.obtenerPorColeccion("producto", "id", productoId);
        if (multimediaList != null && !multimediaList.isEmpty()) {
            return ResponseEntity.ok(multimediaList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    public List<Multimedia> verMultimediaList() {
        return aplicacionMultimedia.obtenerTodos();

    }
}
