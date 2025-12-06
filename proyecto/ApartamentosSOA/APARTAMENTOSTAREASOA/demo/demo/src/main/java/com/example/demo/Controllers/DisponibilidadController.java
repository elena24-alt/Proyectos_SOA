package com.example.demo.Controllers;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.DisponibilidadModel;
import com.example.demo.Services.DisponibilidadService;

@RestController
@RequestMapping("/api/v1/properties/availability")
public class DisponibilidadController {

    @Autowired
    private DisponibilidadService availabilityManagementService;

    //Get all disponibilidad
    @GetMapping()
    public List<DisponibilidadModel> fetchAllAvailabilityRecords(){
        return availabilityManagementService.findAllAvailabilityRecords();
    }

    //Get disponibilidad by id
    @GetMapping("/{id}")
    public ResponseEntity<DisponibilidadModel> fetchAvailabilityById(@PathVariable Long id){
        Optional<DisponibilidadModel> availabilityRecord = availabilityManagementService.findAvailabilityById(id);
        return availabilityRecord.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Create disponibilidad
    @PostMapping
    public DisponibilidadModel registerNewAvailability(@RequestBody DisponibilidadModel availabilityData){
        return availabilityManagementService.persistAvailabilityData(availabilityData);
    }

    //Update disponibilidad
    @PutMapping("/{id}")
    public ResponseEntity<DisponibilidadModel> modifyAvailabilityRecord(@PathVariable Long id, @RequestBody DisponibilidadModel availabilityData){
        Optional<DisponibilidadModel> existingAvailability = availabilityManagementService.findAvailabilityById(id);

        if (existingAvailability.isPresent()) {
            DisponibilidadModel recordToModify = existingAvailability.get();
            recordToModify.setCalendarDate(availabilityData.getCalendarDate());
            recordToModify.setIsAvailable(availabilityData.getIsAvailable());
            recordToModify.setDynamicPricingRate(availabilityData.getDynamicPricingRate());


            DisponibilidadModel modifiedRecord = availabilityManagementService.persistAvailabilityData(recordToModify);
            return ResponseEntity.ok(modifiedRecord);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Delete disponibilidad
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAvailabilityRecord(@PathVariable Long id){
        Optional<DisponibilidadModel> existingAvailability = availabilityManagementService.findAvailabilityById(id);
        if (existingAvailability.isPresent()) {
            availabilityManagementService.deleteAvailabilityRecord(id);
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
        
        List<DisponibilidadModel> availableRecords = availabilityManagementService.validateAvailabilityRange(
                propiedadId, 
                fechaEntrada, 
                fechaSalida
        );
        return ResponseEntity.ok(availableRecords);
    }

}
