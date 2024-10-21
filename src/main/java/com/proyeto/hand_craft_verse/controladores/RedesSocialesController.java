package com.proyeto.hand_craft_verse.controladores;

import com.proyeto.hand_craft_verse.aplicacion.IAplicacion;
import com.proyeto.hand_craft_verse.dominio.rrss.RedesSociales;
import com.proyeto.hand_craft_verse.dominio.rrss.RedesSocialesId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/redes-sociales")
public class RedesSocialesController {

    @Autowired
    IAplicacion<RedesSociales> aplicacionRedesSociales;

    @GetMapping("/{nombre_usuario}/{plataforma}")
    public ResponseEntity<RedesSociales> viewRedesSociales(@PathVariable String nombre_usuario, @PathVariable String plataforma) {
        RedesSocialesId rrssId = new RedesSocialesId(nombre_usuario, plataforma);
        RedesSociales rrss = aplicacionRedesSociales.buscar(rrssId);
        if (rrss != null) {
            return ResponseEntity.ok(rrss);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{nombre_usuario}/{plataforma}")
    public ResponseEntity<Void> deleteRedesSociales(@PathVariable String nombre_usuario, @PathVariable String plataforma) {
        RedesSocialesId rrssId = new RedesSocialesId(nombre_usuario, plataforma);
        if (aplicacionRedesSociales.eliminar(rrssId)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<RedesSociales> addRedesSociales(@RequestBody RedesSociales redesSociales) {
        try {
            if (aplicacionRedesSociales.guardar(redesSociales)) {
                return ResponseEntity.status(HttpStatus.CREATED).body(redesSociales);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/update/{nombre_usuario}/{plataforma}")
    public ResponseEntity<Void> updateRedesSociales(@PathVariable String nombre_usuario, @PathVariable String plataforma, @RequestBody RedesSociales redesSociales) {
        RedesSocialesId rrssId = new RedesSocialesId(nombre_usuario, plataforma);
        RedesSociales existingRrss = aplicacionRedesSociales.buscar(rrssId);
        if (existingRrss != null) {
            existingRrss.setVendedor(redesSociales.getVendedor()); // Actualiza el vendedor
            // Aquí puedes agregar más campos si es necesario

            if (aplicacionRedesSociales.actualizar(existingRrss) != null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<RedesSociales>> verRedesSocialesList() {
        List<RedesSociales> redesSociales = aplicacionRedesSociales.obtenerTodos();
        return ResponseEntity.ok(redesSociales);
    }
}
