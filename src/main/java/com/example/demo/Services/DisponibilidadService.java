package com.example.demo.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.DisponibilidadModel;
import com.example.demo.Models.PropiedadesModel;
import com.example.demo.Repositories.IDisponibilidadRepository;

@Service
public class DisponibilidadService {
    
    @Autowired
    private IDisponibilidadRepository disponibilidadRepository;

    @Autowired
    private PropiedadesService propiedadesService;

    public List<DisponibilidadModel> getAllDisponibilidades() {
        return disponibilidadRepository.findAll();
    }

    public Optional<DisponibilidadModel> getDisponibilidadById(Long id) {
        return disponibilidadRepository.findById(id);
    }

    public DisponibilidadModel saveDisponibilidad(DisponibilidadModel disponibilidad) {
        return disponibilidadRepository.save(disponibilidad);
    }

    public void deleteDisponibilidad(Long id) {
        disponibilidadRepository.deleteById(id);
    }

    //Metodo para buscar por disponibilidad
    public List<DisponibilidadModel> checkDisponibilidad(Long propiedadId, LocalDate fechaEntrada, LocalDate fechaSalida) {
        // 1. Buscar la entidad Propiedad por ID
        PropiedadesModel propiedad = propiedadesService.getPropiedadById(propiedadId)
            .orElseThrow(() -> new RuntimeException("Propiedad no encontrada con ID: " + propiedadId));
            return disponibilidadRepository.findAvailableDatesForProperty(
                propiedad, 
                fechaEntrada, 
                fechaSalida
        );
    }
    
}