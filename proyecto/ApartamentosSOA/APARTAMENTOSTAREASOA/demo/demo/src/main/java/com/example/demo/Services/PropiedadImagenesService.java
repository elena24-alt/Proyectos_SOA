package com.example.demo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.PropiedadImagenesModel;
import com.example.demo.Repositories.IPropiedadImagenesRepository;

@Service
public class PropiedadImagenesService {
    
    @Autowired
    private IPropiedadImagenesRepository mediaGalleryRepository;

    public List<PropiedadImagenesModel> fetchAllImages() {
        return mediaGalleryRepository.findAll();
    }

    public Optional<PropiedadImagenesModel> findImageById(Long id) {
        return mediaGalleryRepository.findById(id);
    }

    public PropiedadImagenesModel persistImageData(PropiedadImagenesModel imageData) {
        return mediaGalleryRepository.save(imageData);
    }

    public void removeImageById(Long id) {
        mediaGalleryRepository.deleteById(id);
    }

}
