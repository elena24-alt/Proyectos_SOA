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
    private IDisponibilidadRepository availabilityRepository;

    @Autowired
    private PropiedadesService propertyManagementService;

    public List<DisponibilidadModel> findAllAvailabilityRecords() {
        return availabilityRepository.findAll();
    }

    public Optional<DisponibilidadModel> findAvailabilityById(Long id) {
        return availabilityRepository.findById(id);
    }

    public DisponibilidadModel persistAvailabilityData(DisponibilidadModel availabilityData) {
        return availabilityRepository.save(availabilityData);
    }

    public void deleteAvailabilityRecord(Long id) {
        availabilityRepository.deleteById(id);
    }

    //Metodo para buscar por disponibilidad
    public List<DisponibilidadModel> validateAvailabilityRange(Long propiedadId, LocalDate fechaEntrada, LocalDate fechaSalida) {
        // 1. Buscar la entidad Propiedad por ID
        PropiedadesModel property = propertyManagementService.findPropertyById(propiedadId)
            .orElseThrow(() -> new RuntimeException("Propiedad no encontrada con ID: " + propiedadId));
            return availabilityRepository.findAvailableDatesForProperty(
                property, 
                fechaEntrada, 
                fechaSalida
        );
    }
    
}
