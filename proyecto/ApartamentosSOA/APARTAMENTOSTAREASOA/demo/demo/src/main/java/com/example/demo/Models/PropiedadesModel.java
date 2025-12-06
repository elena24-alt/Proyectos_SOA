package com.example.demo.Models;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_propiedades_alojamiento")
public class PropiedadesModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prop_fk_propietario", referencedColumnName = "usr_id" ,nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UsuariosModel propertyOwner;

    @Enumerated(EnumType.STRING)
    @Column(name = "prop_clasificacion_tipo", nullable = false)
    private PropertyType propertyClassification;

    @Column(name = "prop_titulo_listado", nullable = false, length = 255)
    private String listingTitle;

    @Lob
    @Column(name = "prop_descripcion_completa", nullable = false)
    private String fullDescription;

    @Column(name = "prop_domicilio_ubicacion", nullable = false, length = 255)
    private String physicalAddress;

    @Column(name = "prop_ciudad_localidad", nullable = false, length = 100)
    private String cityLocation;

    @Column(name = "prop_codigo_area_postal", length = 10)
    private String postalCodeArea;

    @Column(name = "prop_pais_nacion", length = 50)
    private String nationCountry;

    @Column(name = "prop_estado_region", length = 50)
    private String regionState;

    @Column(name = "prop_latitud_gps", precision = 10, scale = 8)
    private BigDecimal gpsLatitude;

    @Column(name = "prop_longitud_gps", precision = 11, scale = 8)
    private BigDecimal gpsLongitude;

    @Column(name = "prop_tarifa_noche_usd", nullable = false, precision = 10, scale = 2)
    private BigDecimal nightlyRateUSD;

    @Column(name = "prop_ocupancia_maxima_personas", nullable = false, length = 3)
    private Integer maxOccupancyCount;

    @Column(name = "prop_cuartos_dormitorios")
    private Integer bedroomQuantity;

    @Column(name = "prop_sanitarios_banos")
    private Integer bathroomQuantity;

    @Column(name = "prop_area_metros_cuadrados")
    private Integer squareMetersArea;

    @Column(name = "prop_servicios_amenities", length = 255)
    private String amenitiesServices;

    @Column(name = "prop_reglas_normas_casa", length = 255)
    private String houseRulesNorms;

    @Enumerated(EnumType.STRING)
    @Column(name = "prop_estado_disponibilidad", nullable = false)
    private AvailabilityStatus availabilityStatus;

    @Column(name = "prop_timestamp_creacion", nullable = false, updatable = false)
    private LocalDateTime createdAtTimestamp;

    @Column(name = "prop_timestamp_actualizacion", nullable = false )
    private LocalDateTime lastUpdatedTimestamp;

    public enum PropertyType{
        SINGLE_UNIT,
        BUSINESS_ENTITY,
        PROPERTY_AGENCY
    }

    public enum AvailabilityStatus{
        READY_TO_BOOK,
        TEMPORARILY_UNAVAILABLE,
        MAINTENANCE_PERIOD
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuariosModel getPropertyOwner() {
        return propertyOwner;
    }

    public void setPropertyOwner(UsuariosModel propertyOwner) {
        this.propertyOwner = propertyOwner;
    }

    public PropertyType getPropertyClassification() {
        return propertyClassification;
    }

    public void setPropertyClassification(PropertyType propertyClassification) {
        this.propertyClassification = propertyClassification;
    }

    public String getListingTitle() {
        return listingTitle;
    }

    public void setListingTitle(String listingTitle) {
        this.listingTitle = listingTitle;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getCityLocation() {
        return cityLocation;
    }

    public void setCityLocation(String cityLocation) {
        this.cityLocation = cityLocation;
    }

    public String getPostalCodeArea() {
        return postalCodeArea;
    }

    public void setPostalCodeArea(String postalCodeArea) {
        this.postalCodeArea = postalCodeArea;
    }

    public String getNationCountry() {
        return nationCountry;
    }

    public void setNationCountry(String nationCountry) {
        this.nationCountry = nationCountry;
    }

    public String getRegionState() {
        return regionState;
    }

    public void setRegionState(String regionState) {
        this.regionState = regionState;
    }

    public BigDecimal getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(BigDecimal gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public BigDecimal getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(BigDecimal gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public BigDecimal getNightlyRateUSD() {
        return nightlyRateUSD;
    }

    public void setNightlyRateUSD(BigDecimal nightlyRateUSD) {
        this.nightlyRateUSD = nightlyRateUSD;
    }

    public Integer getMaxOccupancyCount() {
        return maxOccupancyCount;
    }

    public void setMaxOccupancyCount(Integer maxOccupancyCount) {
        this.maxOccupancyCount = maxOccupancyCount;
    }

    public Integer getBedroomQuantity() {
        return bedroomQuantity;
    }

    public void setBedroomQuantity(Integer bedroomQuantity) {
        this.bedroomQuantity = bedroomQuantity;
    }

    public Integer getBathroomQuantity() {
        return bathroomQuantity;
    }

    public void setBathroomQuantity(Integer bathroomQuantity) {
        this.bathroomQuantity = bathroomQuantity;
    }

    public Integer getSquareMetersArea() {
        return squareMetersArea;
    }

    public void setSquareMetersArea(Integer squareMetersArea) {
        this.squareMetersArea = squareMetersArea;
    }

    public String getAmenitiesServices() {
        return amenitiesServices;
    }

    public void setAmenitiesServices(String amenitiesServices) {
        this.amenitiesServices = amenitiesServices;
    }

    public String getHouseRulesNorms() {
        return houseRulesNorms;
    }

    public void setHouseRulesNorms(String houseRulesNorms) {
        this.houseRulesNorms = houseRulesNorms;
    }

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public LocalDateTime getCreatedAtTimestamp() {
        return createdAtTimestamp;
    }

    public void setCreatedAtTimestamp(LocalDateTime createdAtTimestamp) {
        this.createdAtTimestamp = createdAtTimestamp;
    }

    public LocalDateTime getLastUpdatedTimestamp() {
        return lastUpdatedTimestamp;
    }

    public void setLastUpdatedTimestamp(LocalDateTime lastUpdatedTimestamp) {
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }

    @PrePersist
    protected void onCreate() {
        createdAtTimestamp = LocalDateTime.now();
        lastUpdatedTimestamp = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdatedTimestamp = LocalDateTime.now();
    }

    //Constructors
    public PropiedadesModel() {
    }

    //Constructors with no id, createdAtTimestamp, lastUpdatedTimestamp
    public PropiedadesModel(UsuariosModel propertyOwner, PropertyType propertyClassification, String listingTitle, String fullDescription,
            String physicalAddress, String cityLocation, String postalCodeArea, String nationCountry, String regionState,
            BigDecimal gpsLatitude, BigDecimal gpsLongitude, BigDecimal nightlyRateUSD, Integer maxOccupancyCount,
            Integer bedroomQuantity, Integer bathroomQuantity, Integer squareMetersArea, String amenitiesServices,
            String houseRulesNorms, AvailabilityStatus availabilityStatus) {
        this.propertyOwner = propertyOwner;
        this.propertyClassification = propertyClassification;
        this.listingTitle = listingTitle;
        this.fullDescription = fullDescription;
        this.physicalAddress = physicalAddress; 
        this.cityLocation = cityLocation;
        this.postalCodeArea = postalCodeArea;
        this.nationCountry = nationCountry;
        this.regionState = regionState;
        this.gpsLatitude = gpsLatitude;
        this.gpsLongitude = gpsLongitude;
        this.nightlyRateUSD = nightlyRateUSD;
        this.maxOccupancyCount = maxOccupancyCount;
        this.bedroomQuantity = bedroomQuantity;
        this.bathroomQuantity = bathroomQuantity;
        this.squareMetersArea = squareMetersArea;
        this.amenitiesServices = amenitiesServices;
        this.houseRulesNorms = houseRulesNorms;
        this.availabilityStatus = availabilityStatus;
    }
    
}
