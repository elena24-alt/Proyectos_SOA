package com.example.demo.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.PropiedadImagenesModel;
import com.example.demo.Services.PropiedadImagenesService;

@RestController
@RequestMapping("/propiedad-imagenes")
public class PropiedadImagenesController {
    
    @Autowired
    private PropiedadImagenesService propiedadImagenesService;

    // Get all images
    @GetMapping
    public List<PropiedadImagenesModel> getAllImagenes() {
        return propiedadImagenesService.getAllImagenes();
    }

    // Get image by ID
    @GetMapping("/{id}")
    public ResponseEntity<PropiedadImagenesModel> getImagenById(@PathVariable Long id) {
        Optional<PropiedadImagenesModel> imagen = propiedadImagenesService.getImagenById(id);
        return imagen.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create image
    @PostMapping
    public PropiedadImagenesModel createImagen(@RequestBody PropiedadImagenesModel imagen) {
        return propiedadImagenesService.saveImagen(imagen);
    }

    // Update image
    @PutMapping("/{id}")
    public ResponseEntity<PropiedadImagenesModel> updateImagen(@PathVariable Long id, @RequestBody PropiedadImagenesModel imagen) {
        Optional<PropiedadImagenesModel> existingImagen = propiedadImagenesService.getImagenById(id);

        if (existingImagen.isPresent()) {
            PropiedadImagenesModel imagenToUpdate = existingImagen.get();
            imagenToUpdate.setUrlImagen(imagen.getUrlImagen());
            imagenToUpdate.setOrden(imagen.getOrden());
            imagenToUpdate.setEsPrincipal(imagen.getEsPrincipal());

            PropiedadImagenesModel updatedImagen = propiedadImagenesService.saveImagen(imagenToUpdate);
            return ResponseEntity.ok(updatedImagen);
        } else {
            return ResponseEntity.notFound().build();
        
        }
        
    }

    // Delete image
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImagen(@PathVariable Long id) {
        Optional<PropiedadImagenesModel> existingImagen = propiedadImagenesService.getImagenById(id);
        if (existingImagen.isPresent()) {
            propiedadImagenesService.deleteImagen(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}