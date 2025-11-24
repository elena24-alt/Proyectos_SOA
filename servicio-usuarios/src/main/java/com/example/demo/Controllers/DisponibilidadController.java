package com.example.demo.Controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.DisponibilidadModel;
import com.example.demo.Services.DisponibilidadService;

@RestController
@RequestMapping("/disponibilidad")
public class DisponibilidadController {

    @Autowired
    private DisponibilidadService disponibilidadService;

    //Get all disponibilidad
    @GetMapping()
    public List<DisponibilidadModel> getAllDisponibilidades(){
        return disponibilidadService.getAllDisponibilidades();
    }

    //Get disponibilidad by id
    @GetMapping("/{id}")
    public ResponseEntity<DisponibilidadModel> getDisponibilidadById(@PathVariable Long id){
        Optional<DisponibilidadModel> disponibilidad = disponibilidadService.getDisponibilidadById(id);
        return disponibilidad.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Create disponibilidad
    @PostMapping
    public DisponibilidadModel createDisponibilidad(@RequestBody DisponibilidadModel disponibilidad){
        return disponibilidadService.saveDisponibilidad(disponibilidad);
    }

    //Update disponibilidad
    @PutMapping("/{id}")
    public ResponseEntity<DisponibilidadModel> updateDisponibilidad(@PathVariable Long id, @RequestBody DisponibilidadModel disponibilidad){
        Optional<DisponibilidadModel> existingDisponibilidad = disponibilidadService.getDisponibilidadById(id);

        if (existingDisponibilidad.isPresent()) {
            DisponibilidadModel disponibilidadToUpdate = existingDisponibilidad.get();
            disponibilidadToUpdate.setFecha(disponibilidad.getFecha());
            disponibilidadToUpdate.setDisponible(disponibilidad.getDisponible());
            disponibilidadToUpdate.setPrecioEspecial(disponibilidad.getPrecioEspecial());


            DisponibilidadModel updatedDisponibilidad = disponibilidadService.saveDisponibilidad(disponibilidadToUpdate);
            return ResponseEntity.ok(updatedDisponibilidad);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Delete disponibilidad
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisponibilidad(@PathVariable Long id){
        Optional<DisponibilidadModel> existingDisponibilidad = disponibilidadService.getDisponibilidadById(id);
        if (existingDisponibilidad.isPresent()) {
            disponibilidadService.deleteDisponibilidad(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Check disponibilidad 
    @GetMapping("/check")
    public ResponseEntity<List<DisponibilidadModel>> checkAvailability(
        @RequestParam Long propiedadId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaEntrada,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaSalida) {
        
        List<DisponibilidadModel> disponibles = disponibilidadService.checkDisponibilidad(
                propiedadId, 
                fechaEntrada, 
                fechaSalida
        );
        return ResponseEntity.ok(disponibles);
    }

}