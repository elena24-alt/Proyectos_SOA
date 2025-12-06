package com.example.demo.Models;


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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_transacciones_pagos")
public class PagosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pgo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "pgo_fk_reserva", referencedColumnName = "rsrv_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ReservacionesModel relatedReservation;

    @Column(name = "pgo_monto_transaccional", nullable = false)
    private Double transactionAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "pgo_metodo_instrumento", nullable = false)
    private PaymentMethod paymentInstrument;

    @Enumerated(EnumType.STRING)
    @Column(name = "pgo_estado_transaccion", nullable = false)
    private TransactionStatus transactionStatus;

    @Column(name = "pgo_timestamp_procesamiento", nullable = false)
    private LocalDateTime processingTimestamp;

    @Column(name = "pgo_codigo_confirmacion", nullable = false)
    private String confirmationCode;

    @Column(name = "pgo_datos_procesador", nullable = false)
    private String processorMetadata;

    @Column(name = "pgo_timestamp_creacion", nullable = false)
    private LocalDateTime creationTimestamp;

    public enum PaymentMethod {
        CREDIT_CARD,
        DEBIT_CARD,
        CASH_ON_SITE,
    }

    public enum TransactionStatus {
        AWAITING_CONFIRMATION,
        SUCCESSFULLY_PROCESSED,
        PROCESSING_FAILED,
    }

    @PrePersist
    protected void onCreate() {
        this.creationTimestamp = LocalDateTime.now();
        this.processingTimestamp = LocalDateTime.now();
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservacionesModel getRelatedReservation() {
        return relatedReservation;
    }

    public void setRelatedReservation(ReservacionesModel relatedReservation) {
        this.relatedReservation = relatedReservation;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public PaymentMethod getPaymentInstrument() {
        return paymentInstrument;
    }

    public void setPaymentInstrument(PaymentMethod paymentInstrument) {
        this.paymentInstrument = paymentInstrument;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public LocalDateTime getProcessingTimestamp() {
        return processingTimestamp;
    }

    public void setProcessingTimestamp(LocalDateTime processingTimestamp) {
        this.processingTimestamp = processingTimestamp;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public String getProcessorMetadata() {
        return processorMetadata;
    }

    public void setProcessorMetadata(String processorMetadata) {
        this.processorMetadata = processorMetadata;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    //Constructors
    public PagosModel() {
    }

    //Constructor with all fields except id and creationTimestamp
    public PagosModel(ReservacionesModel relatedReservation, Double transactionAmount, PaymentMethod paymentInstrument, 
        TransactionStatus transactionStatus, LocalDateTime processingTimestamp, String confirmationCode, String processorMetadata, LocalDateTime creationTimestamp) {

        this.relatedReservation = relatedReservation;
        this.transactionAmount = transactionAmount;
        this.paymentInstrument = paymentInstrument;
        this.transactionStatus = transactionStatus;
        this.processingTimestamp = processingTimestamp;
        this.confirmationCode = confirmationCode;
        this.processorMetadata = processorMetadata;
        this.creationTimestamp = creationTimestamp;
    }

}

