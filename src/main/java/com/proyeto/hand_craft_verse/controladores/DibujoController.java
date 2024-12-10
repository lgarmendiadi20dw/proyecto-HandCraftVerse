package com.proyeto.hand_craft_verse.controladores;
import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.proyeto.hand_craft_verse.aplicacion.IAplicacion;
import com.proyeto.hand_craft_verse.dominio.Dibujo;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;

import java.io.InputStream;
import java.io.FileInputStream;


@RestController
@RequestMapping("/dibujo")
@AllArgsConstructor
@CrossOrigin
public class DibujoController {
    @Autowired
    private IAplicacion<Dibujo> aplicacionDibujo;
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestPart("file") MultipartFile file) {
        try {
            Dibujo dibujo = new Dibujo(file.getOriginalFilename().split("\\.")[0], file);

            aplicacionDibujo.guardar(dibujo);

            return new ResponseEntity<>("Dibujo subido correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al subir el dibujo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{param}")
    public ResponseEntity<InputStreamResource> download(@PathVariable String param) {
        try {
            Dibujo dibujo = aplicacionDibujo.buscar(param);

            InputStream is = new FileInputStream(dibujo.getFile());

            InputStreamResource resource = new InputStreamResource(is);

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + dibujo.getFile().getName())
                    .body(resource); 

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
