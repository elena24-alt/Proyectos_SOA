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
@RequestMapping("/api/v1/properties/gallery/media")
public class PropiedadImagenesController {
    
    @Autowired
    private PropiedadImagenesService mediaGalleryService;

    // Get all images
    @GetMapping
    public List<PropiedadImagenesModel> fetchAllImages() {
        return mediaGalleryService.fetchAllImages();
    }

    // Get image by ID
    @GetMapping("/{id}")
    public ResponseEntity<PropiedadImagenesModel> fetchImageById(@PathVariable Long id) {
        Optional<PropiedadImagenesModel> imageRecord = mediaGalleryService.findImageById(id);
        return imageRecord.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create image
    @PostMapping
    public PropiedadImagenesModel registerNewImage(@RequestBody PropiedadImagenesModel imageData) {
        return mediaGalleryService.persistImageData(imageData);
    }

    // Update image
    @PutMapping("/{id}")
    public ResponseEntity<PropiedadImagenesModel> modifyImageRecord(@PathVariable Long id, @RequestBody PropiedadImagenesModel imageData) {
        Optional<PropiedadImagenesModel> existingImage = mediaGalleryService.findImageById(id);

        if (existingImage.isPresent()) {
            PropiedadImagenesModel recordToModify = existingImage.get();
            recordToModify.setImageResourceUrl(imageData.getImageResourceUrl());
            recordToModify.setDisplayOrder(imageData.getDisplayOrder());
            recordToModify.setIsFeaturedImage(imageData.getIsFeaturedImage());

            PropiedadImagenesModel modifiedRecord = mediaGalleryService.persistImageData(recordToModify);
            return ResponseEntity.ok(modifiedRecord);
        } else {
            return ResponseEntity.notFound().build();
        
        }
        
    }

    // Delete image
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeImageById(@PathVariable Long id) {
        Optional<PropiedadImagenesModel> existingImage = mediaGalleryService.findImageById(id);
        if (existingImage.isPresent()) {
            mediaGalleryService.removeImageById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
