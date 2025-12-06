package com.example.demo.Models;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_media_galeria_fotos")
public class PropiedadImagenesModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "img_fk_propiedad", referencedColumnName = "prop_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private PropiedadesModel propertyGallery;

    @Column(name = "img_url_recurso_imagen", nullable = false, length = 500)
    private String imageResourceUrl;

    @Column(name = "img_posicion_orden", nullable = false)
    private Integer displayOrder;

    @Column(name = "img_imagen_principal", nullable = false)
    private Boolean isFeaturedImage;

    @Column(name = "img_timestamp_subida", nullable = false)
    private LocalDateTime uploadedAtTimestamp;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PropiedadesModel getPropertyGallery() {
        return propertyGallery;
    }

    public void setPropertyGallery(PropiedadesModel propertyGallery) {
        this.propertyGallery = propertyGallery;
    }

    public String getImageResourceUrl() {
        return imageResourceUrl;
    }

    public void setImageResourceUrl(String imageResourceUrl) {
        this.imageResourceUrl = imageResourceUrl;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Boolean getIsFeaturedImage() {
        return isFeaturedImage;
    }

    public void setIsFeaturedImage(Boolean isFeaturedImage) {
        this.isFeaturedImage = isFeaturedImage;
    }

    public LocalDateTime getUploadedAtTimestamp() {
        return uploadedAtTimestamp;
    }

    public void setUploadedAtTimestamp(LocalDateTime uploadedAtTimestamp) {
        this.uploadedAtTimestamp = uploadedAtTimestamp;
    }

    @PrePersist
    protected void onCreate() {
        uploadedAtTimestamp = LocalDateTime.now();
    }

    //Constructors

    public PropiedadImagenesModel() {
    }

    //Constructor with no id
    public PropiedadImagenesModel(PropiedadesModel propertyGallery, String imageResourceUrl, Integer displayOrder, Boolean isFeaturedImage) {
        this.propertyGallery = propertyGallery;
        this.imageResourceUrl = imageResourceUrl;
        this.displayOrder = displayOrder;
        this.isFeaturedImage = isFeaturedImage;
        this.uploadedAtTimestamp = LocalDateTime.now();
    }
}
