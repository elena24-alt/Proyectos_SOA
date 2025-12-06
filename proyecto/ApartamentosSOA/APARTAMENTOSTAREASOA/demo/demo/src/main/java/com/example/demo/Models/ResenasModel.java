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
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_reviews_valoraciones")
public class ResenasModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rvw_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "rvw_fk_reserva_asociada", referencedColumnName = "rsrv_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ReservacionesModel associatedReservation;

    @Column(name = "rvw_puntuacion_limpieza")
    private Integer cleanlinessScore;

    @Column(name = "rvw_puntuacion_ubicacion")
    private Integer locationScore;

    @Column(name = "rvw_puntuacion_atencion")
    private Integer communicationScore;

    @Column(name = "rvw_puntuacion_promedio", nullable = false)
    private Integer overallRating;

    @Lob 
    @Column(name = "rvw_texto_comentario")
    private String reviewComment;

    @Column(name = "rvw_timestamp_publicacion", nullable = false, updatable = false)
    private LocalDateTime publishedAtTimestamp;

    @Lob 
    @Column(name = "rvw_respuesta_dueno")
    private String ownerResponse;

    @Column(name = "rvw_timestamp_respuesta")
    private LocalDateTime responseTimestamp;

    @PrePersist
    protected void onCreate() {
        this.publishedAtTimestamp = LocalDateTime.now();
        this.responseTimestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservacionesModel getAssociatedReservation() {
        return associatedReservation;
    }

    public void setAssociatedReservation(ReservacionesModel associatedReservation) {
        this.associatedReservation = associatedReservation;
    }

    public Integer getCleanlinessScore() {
        return cleanlinessScore;
    }

    public void setCleanlinessScore(Integer cleanlinessScore) {
        this.cleanlinessScore = cleanlinessScore;
    }

    public Integer getLocationScore() {
        return locationScore;
    }

    public void setLocationScore(Integer locationScore) {
        this.locationScore = locationScore;
    }

    public Integer getCommunicationScore() {
        return communicationScore;
    }

    public void setCommunicationScore(Integer communicationScore) {
        this.communicationScore = communicationScore;
    }

    public Integer getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(Integer overallRating) {
        this.overallRating = overallRating;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }

    public LocalDateTime getPublishedAtTimestamp() {
        return publishedAtTimestamp;
    }

    public void setPublishedAtTimestamp(LocalDateTime publishedAtTimestamp) {
        this.publishedAtTimestamp = publishedAtTimestamp;
    }

    public String getOwnerResponse() {
        return ownerResponse;
    }

    public void setOwnerResponse(String ownerResponse) {
        this.ownerResponse = ownerResponse;
    }

    public LocalDateTime getResponseTimestamp() {
        return responseTimestamp;
    }

    public void setResponseTimestamp(LocalDateTime responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }

    //Constructor vacío
    public ResenasModel() {
    }

    //Constructor con parámetros
    public ResenasModel(ReservacionesModel associatedReservation, Integer cleanlinessScore, Integer locationScore,
            Integer communicationScore, Integer overallRating, String reviewComment,
            String ownerResponse) {
        this.cleanlinessScore = cleanlinessScore;
        this.locationScore = locationScore;
        this.communicationScore = communicationScore;
        this.overallRating = overallRating;
        this.reviewComment = reviewComment;
        this.ownerResponse = ownerResponse;
    }

}
