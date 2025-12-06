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
import com.example.demo.Models.PropiedadesModel.PropertyType;
import com.example.demo.Models.UsuariosModel;
import com.example.demo.Services.PropiedadesService;
import com.example.demo.Services.UsuariosService;

@RestController
@RequestMapping("/api/v1/properties")
public class PropiedadesController {
    
    @Autowired
    private PropiedadesService propertyManagementService;

    @Autowired
    private UsuariosService userManagementService;

    //Get all properties
    @GetMapping()
    public List<PropiedadesModel> fetchAllProperties(){
        return propertyManagementService.fetchAllProperties();
    }

    //Get property by id
    @GetMapping("/{id}")
    public ResponseEntity<PropiedadesModel> fetchPropertyById(@PathVariable Long id){
        Optional<PropiedadesModel> property = propertyManagementService.findPropertyById(id);
        return property.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Create property
    @PostMapping
    public PropiedadesModel registerNewProperty(@RequestBody PropiedadesModel property){
        return propertyManagementService.persistPropertyData(property);
    }

    //Update property
    @PutMapping("/{id}")
    public ResponseEntity<PropiedadesModel> modifyPropertyRecord(@PathVariable Long id, @RequestBody PropiedadesModel property){
        Optional<PropiedadesModel> existingProperty = propertyManagementService.findPropertyById(id);

        if (existingProperty.isPresent()) {
            PropiedadesModel propertyToUpdate = existingProperty.get();
            propertyToUpdate.setPhysicalAddress(property.getPhysicalAddress());
            propertyToUpdate.setCityLocation(property.getCityLocation());
            propertyToUpdate.setRegionState(property.getRegionState());
            propertyToUpdate.setPostalCodeArea(property.getPostalCodeArea());
            propertyToUpdate.setNightlyRateUSD(property.getNightlyRateUSD());
            propertyToUpdate.setFullDescription(property.getFullDescription());
            propertyToUpdate.setBedroomQuantity(property.getBedroomQuantity());
            propertyToUpdate.setBathroomQuantity(property.getBathroomQuantity());
            propertyToUpdate.setSquareMetersArea(property.getSquareMetersArea());
            propertyToUpdate.setAmenitiesServices(property.getAmenitiesServices());
            propertyToUpdate.setHouseRulesNorms(property.getHouseRulesNorms());
            propertyToUpdate.setMaxOccupancyCount(property.getMaxOccupancyCount());
            propertyToUpdate.setNationCountry(property.getNationCountry());
            propertyToUpdate.setGpsLatitude(property.getGpsLatitude());
            propertyToUpdate.setGpsLongitude(property.getGpsLongitude());
            propertyToUpdate.setPropertyClassification(property.getPropertyClassification());
            propertyToUpdate.setListingTitle(property.getListingTitle());


            PropiedadesModel updatedProperty = propertyManagementService.persistPropertyData(propertyToUpdate);
            return ResponseEntity.ok(updatedProperty);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Delete property
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removePropertyById(@PathVariable Long id){
        Optional<PropiedadesModel> existingProperty = propertyManagementService.findPropertyById(id);
        if (existingProperty.isPresent()) {
            propertyManagementService.removePropertyById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 1. Endpoint for /api/v1/properties/classification/{classification}
    @GetMapping("/classification/{classification}")
    public ResponseEntity<List<PropiedadesModel>> findPropertiesByClassification(@PathVariable String classification) {
        try {
            PropertyType classificationEnum = PropertyType.valueOf(classification.toUpperCase());
            List<PropiedadesModel> properties = propertyManagementService.findPropertiesByClassification(classificationEnum);
            
            if (properties.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(properties);
        } catch (IllegalArgumentException e) {
            // Handling if path value is not a valid Enum
            return ResponseEntity.badRequest().build();
        }
    }

    // 2. Endpoint for /api/v1/properties/owner/{ownerId}
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<PropiedadesModel>> findPropertiesOwnedBy(@PathVariable Long ownerId) {
        
        Optional<UsuariosModel> ownerOptional = userManagementService.findUserById(ownerId);

        if (!ownerOptional.isPresent()) {
            return ResponseEntity.notFound().build(); // Owner not found
        }
        
        UsuariosModel owner = ownerOptional.get();
        List<PropiedadesModel> properties = propertyManagementService.findPropertiesOwnedBy(owner);

        if (properties.isEmpty()) {
            // Returns OK, but empty list, or NotFound if preferred
            return ResponseEntity.ok(properties); 
        }

        return ResponseEntity.ok(properties);
    }

}
