package com.example.demo.Models;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_reservaciones_hospedaje")
public class ReservacionesModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rsrv_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "rsrv_fk_propiedad", referencedColumnName = "prop_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private PropiedadesModel propertyBooked; 

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "rsrv_fk_usuario_huesped", referencedColumnName = "usr_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UsuariosModel guestUser;

    @Column(name = "rsrv_fecha_llegada", nullable = false)
    private LocalDate checkInDate;

    @Column(name = "rsrv_fecha_partida", nullable = false)
    private LocalDate checkOutDate;

    @Column(name = "rsrv_cantidad_huespedes")
    private Integer totalGuestsCount;

    @Column(name = "rsrv_monto_total_reserva", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalReservationAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "rsrv_estado_actual")
    private BookingStatus currentBookingStatus;

    @Column(name = "rsrv_timestamp_creacion", nullable = false, updatable = false)
    private LocalDateTime createdAtTimestamp;

    @Lob 
    @Column(name = "rsrv_notas_especiales_huespedes")
    private String specialGuestNotes;

    @Column(name = "rsrv_codigo_confirmacion", length = 50) 
    private String confirmationCode;

    @Column(name = "rsrv_timestamp_check_in")
    private LocalDateTime checkInTimestamp;

    @Column(name = "rsrv_timestamp_check_out")
    private LocalDateTime checkOutTimestamp;

    public enum BookingStatus {
        WAITING_CONFIRMATION,
        BOOKING_CONFIRMED,
        BOOKING_CANCELLED,
        COMPLETED_CHECKOUT 
    }

    @PrePersist
    protected void onCreate() {
        createdAtTimestamp = LocalDateTime.now();
        checkInTimestamp = LocalDateTime.now();
        checkOutTimestamp = LocalDateTime.now();
    
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PropiedadesModel getPropertyBooked() {
        return propertyBooked;
    }

    public void setPropertyBooked(PropiedadesModel propertyBooked) {
        this.propertyBooked = propertyBooked;
    }

    public UsuariosModel getGuestUser() {
        return guestUser;
    }

    public void setGuestUser(UsuariosModel guestUser) {
        this.guestUser = guestUser;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Integer getTotalGuestsCount() {
        return totalGuestsCount;
    }

    public void setTotalGuestsCount(Integer totalGuestsCount) {
        this.totalGuestsCount = totalGuestsCount;
    }

    public BigDecimal getTotalReservationAmount() {
        return totalReservationAmount;
    }

    public void setTotalReservationAmount(BigDecimal totalReservationAmount) {
        this.totalReservationAmount = totalReservationAmount;
    }

    public BookingStatus getCurrentBookingStatus() {
        return currentBookingStatus;
    }

    public void setCurrentBookingStatus(BookingStatus currentBookingStatus) {
        this.currentBookingStatus = currentBookingStatus;
    }

    public LocalDateTime getCreatedAtTimestamp() {
        return createdAtTimestamp;
    }

    public void setCreatedAtTimestamp(LocalDateTime createdAtTimestamp) {
        this.createdAtTimestamp = createdAtTimestamp;
    }

    public String getSpecialGuestNotes() {
        return specialGuestNotes;
    }

    public void setSpecialGuestNotes(String specialGuestNotes) {
        this.specialGuestNotes = specialGuestNotes;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public LocalDateTime getCheckInTimestamp() {
        return checkInTimestamp;
    }

    public void setCheckInTimestamp(LocalDateTime checkInTimestamp) {
        this.checkInTimestamp = checkInTimestamp;
    }

    public LocalDateTime getCheckOutTimestamp() {
        return checkOutTimestamp;
    }

    public void setCheckOutTimestamp(LocalDateTime checkOutTimestamp) {
        this.checkOutTimestamp = checkOutTimestamp;
    }

    
    //Constructors
    public ReservacionesModel() {
    }
    
    // Constructor con todos los campos excepto el id
    public ReservacionesModel(Long id, PropiedadesModel propertyBooked, UsuariosModel guestUser, LocalDate checkInDate,
            LocalDate checkOutDate, Integer totalGuestsCount, BigDecimal totalReservationAmount, BookingStatus currentBookingStatus,
            LocalDateTime createdAtTimestamp, String specialGuestNotes, String confirmationCode, LocalDateTime checkInTimestamp,
            LocalDateTime checkOutTimestamp) {
        this.propertyBooked = propertyBooked;
        this.guestUser = guestUser;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalGuestsCount = totalGuestsCount;
        this.totalReservationAmount = totalReservationAmount;
        this.currentBookingStatus = currentBookingStatus;
        this.specialGuestNotes = specialGuestNotes;
        this.confirmationCode = confirmationCode;
        this.checkInTimestamp = checkInTimestamp;
        this.checkOutTimestamp = checkOutTimestamp;
    }
    
}
