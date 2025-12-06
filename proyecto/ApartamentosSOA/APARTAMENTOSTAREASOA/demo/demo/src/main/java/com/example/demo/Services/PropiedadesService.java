package com.example.demo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.PropiedadesModel;
import com.example.demo.Models.PropiedadesModel.PropertyType;
import com.example.demo.Models.UsuariosModel;
import com.example.demo.Repositories.IPropiedadesRepository;

@Service
public class PropiedadesService {
    @Autowired
    private IPropiedadesRepository propertyDataRepository;

    public List<PropiedadesModel> fetchAllProperties() {
        return propertyDataRepository.findAll();
    }

    public Optional<PropiedadesModel> findPropertyById(Long id) {
        return propertyDataRepository.findById(id);
    }

    public PropiedadesModel persistPropertyData(PropiedadesModel propertyData) {
        return propertyDataRepository.save(propertyData);
    }

    public void removePropertyById(Long id) {
        propertyDataRepository.deleteById(id);
    }

    // Método para recuperar propiedades por tipo
    public List<PropiedadesModel> findPropertiesByClassification(PropiedadesModel.PropertyType propertyType) {
        return propertyDataRepository.findByPropertyClassification(propertyType);
    }

    // Método para recuperar todas las propiedades de un Propietario
    public List<PropiedadesModel> findPropertiesOwnedBy(UsuariosModel ownerUser) {
        return propertyDataRepository.findByPropertyOwner(ownerUser);
    }
}
