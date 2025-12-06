package com.example.demo.Models;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_disponibilidad_calendario")
public class DisponibilidadModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "disp_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "disp_fk_propiedad", referencedColumnName = "prop_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private PropiedadesModel propertyReference;

    @Column(name = "disp_fecha_calendario", nullable = false)
    private LocalDate calendarDate; 

    @Column(name = "disp_disponible_flag", nullable = false)
    private Boolean isAvailable; 

    @Column(name = "disp_precio_tarifa_dinamica", precision = 10, scale = 2)
    private BigDecimal dynamicPricingRate;

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PropiedadesModel getPropertyReference() {
        return propertyReference;
    }

    public void setPropertyReference(PropiedadesModel propertyReference) {
        this.propertyReference = propertyReference;
    }

    public LocalDate getCalendarDate() {
        return calendarDate;
    }

    public void setCalendarDate(LocalDate calendarDate) {
        this.calendarDate = calendarDate;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public BigDecimal getDynamicPricingRate() {
        return dynamicPricingRate;
    }

    public void setDynamicPricingRate(BigDecimal dynamicPricingRate) {
        this.dynamicPricingRate = dynamicPricingRate;
    }
    
    //Constructors
    public DisponibilidadModel() {
    }


    public DisponibilidadModel(Long id, PropiedadesModel propertyReference, LocalDate calendarDate, Boolean isAvailable,
            BigDecimal dynamicPricingRate) {
        this.propertyReference = propertyReference;        
        this.calendarDate = calendarDate;
        this.isAvailable = isAvailable;
        this.dynamicPricingRate = dynamicPricingRate;
    }

}
