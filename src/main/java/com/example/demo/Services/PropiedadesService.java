package com.example.demo.Services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.PropiedadesModel;
import com.example.demo.Models.PropiedadesModel.TipoPropiedad;
import com.example.demo.Models.UsuariosModel;
import com.example.demo.Repositories.IPropiedadesRepository;

@Service
public class PropiedadesService {
    @Autowired
    private IPropiedadesRepository propiedadesRepository;

    public List<PropiedadesModel> getAllPropiedades() {
        return propiedadesRepository.findAll();
    }

    public Optional<PropiedadesModel> getPropiedadById(Long id) {
        return propiedadesRepository.findById(id);
    }

    public PropiedadesModel savePropiedad(PropiedadesModel propiedad) {
        return propiedadesRepository.save(propiedad);
    }

    public void deletePropiedad(Long id) {
        propiedadesRepository.deleteById(id);
    }

    
    public List<PropiedadesModel> getPropiedadesByTipo(TipoPropiedad tipo) {
        return propiedadesRepository.findByTipo(tipo);
    }

    
    public List<PropiedadesModel> getPropiedadesByPropietario(UsuariosModel propietario) {
        return propiedadesRepository.findByPropietario(propietario);
    }
}