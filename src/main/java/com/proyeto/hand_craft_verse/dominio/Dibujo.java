package com.proyeto.hand_craft_verse.dominio;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Dibujo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;

    private String path;

    public Dibujo(String nombre, MultipartFile file) throws IllegalStateException, IOException {


        this.nombre = nombre;

        this.path = "/HandCraftVerse/" + file.getOriginalFilename();

        File newFile = new File(this.path);

        file.transferTo(newFile);
    
    }

    public File getFile() throws IOException {
        File file = new File(this.path);
        return file;

    }
    
    
}
