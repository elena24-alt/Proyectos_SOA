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

import com.example.demo.Models.PropiedadesModel;
import com.example.demo.Models.PropiedadesModel.TipoPropiedad;
import com.example.demo.Models.UsuariosModel;
import com.example.demo.Services.PropiedadesService;
import com.example.demo.Services.UsuariosService;

@RestController
@RequestMapping("/propiedades")
public class PropiedadesController {
    
    @Autowired
    private PropiedadesService propiedadesService;

    @Autowired
    private UsuariosService usuariosService;

    @GetMapping()
    public List<PropiedadesModel> getAllPropiedades(){
        return propiedadesService.getAllPropiedades();
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<PropiedadesModel> getPropiedadById(@PathVariable Long id){
        Optional<PropiedadesModel> propiedad = propiedadesService.getPropiedadById(id);
        return propiedad.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    @PostMapping
    public PropiedadesModel createPropiedad(@RequestBody PropiedadesModel propiedad){
        return propiedadesService.savePropiedad(propiedad);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<PropiedadesModel> updatePropiedad(@PathVariable Long id, @RequestBody PropiedadesModel propiedad){
        Optional<PropiedadesModel> existingPropiedad = propiedadesService.getPropiedadById(id);

        if (existingPropiedad.isPresent()) {
            PropiedadesModel propiedadToUpdate = existingPropiedad.get();
            propiedadToUpdate.setDireccion(propiedad.getDireccion());
            propiedadToUpdate.setCiudad(propiedad.getCiudad());
            propiedadToUpdate.setEstado(propiedad.getEstado());
            propiedadToUpdate.setCodigoPostal(propiedad.getCodigoPostal());
            propiedadToUpdate.setPrecioNoche(propiedad.getPrecioNoche());
            propiedadToUpdate.setDescripcion(propiedad.getDescripcion());
            propiedadToUpdate.setNumeroHabitaciones(propiedad.getNumeroHabitaciones());
            propiedadToUpdate.setNumeroBaños(propiedad.getNumeroBaños());
            propiedadToUpdate.setMetrosCuadrados(propiedad.getMetrosCuadrados());
            propiedadToUpdate.setComodidades(propiedad.getComodidades());
            propiedadToUpdate.setNormasCasa(propiedad.getNormasCasa());
            propiedadToUpdate.setCapacidadPersonas(propiedad.getCapacidadPersonas());
            propiedadToUpdate.setPais(propiedad.getPais());
            propiedadToUpdate.setEstadoProvincia(propiedad.getEstadoProvincia());
            propiedadToUpdate.setLatitud(propiedad.getLatitud());
            propiedadToUpdate.setLongitud(propiedad.getLongitud());
            propiedadToUpdate.setTipo(propiedad.getTipo());
            propiedadToUpdate.setTitulo(propiedad.getTitulo());


            PropiedadesModel updatedPropiedad = propiedadesService.savePropiedad(propiedadToUpdate);
            return ResponseEntity.ok(updatedPropiedad);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePropiedad(@PathVariable Long id){
        Optional<PropiedadesModel> existingPropiedad = propiedadesService.getPropiedadById(id);
        if (existingPropiedad.isPresent()) {
            propiedadesService.deletePropiedad(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<PropiedadesModel>> getPropiedadesByTipo(@PathVariable String tipo) {
        try {
            TipoPropiedad tipoEnum = TipoPropiedad.valueOf(tipo.toUpperCase());
            List<PropiedadesModel> propiedades = propiedadesService.getPropiedadesByTipo(tipoEnum);
            
            if (propiedades.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(propiedades);
        } catch (IllegalArgumentException e) {
            // Manejo si el valor del path no es un Enum válido
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/propietario/{propietarioId}")
    public ResponseEntity<List<PropiedadesModel>> getPropiedadesByPropietario(@PathVariable Long propietarioId) {
        
        Optional<UsuariosModel> propietarioOptional = usuariosService.getUsuarioById(propietarioId);

        if (propietarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); // Propietario no encontrado
        }
        
        UsuariosModel propietario = propietarioOptional.get();
        List<PropiedadesModel> propiedades = propiedadesService.getPropiedadesByPropietario(propietario);

        if (propiedades.isEmpty()) {
            // Retorna OK, pero lista vacía, o NotFound si prefieres
            return ResponseEntity.ok(propiedades); 
        }

        return ResponseEntity.ok(propiedades);
    }

}