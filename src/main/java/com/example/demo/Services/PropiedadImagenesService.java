package com.example.demo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.Models.PropiedadImagenesModel;
import com.example.demo.Repositories.IPropiedadImagenesRepository;


@Service

public class PropiedadImagenesService {
    private IPropiedadImagenesRepository propiedadImagenesRepository;

    public List<PropiedadImagenesModel> getAllImagenes(){
        return propiedadImagenesRepository.findAll();
    }

    public Optional<PropiedadImagenesModel> getImagenById(Long id){
        return propiedadImagenesRepository.findById(id);
    }

    public PropiedadImagenesModel saveImagen(PropiedadImagenesModel imagen){
        return propiedadImagenesRepository.save(imagen);

    }

    public void deleteImagen(Long id){
        propiedadImagenesRepository.deleteById(id);
     }

    
}
