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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_mensajeria_sistema")
public class MensajesModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msg_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "msg_fk_usuario_origen",
        referencedColumnName = "usr_id",
        nullable = false
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "propiedades", "reservasEnviadas", "reservasRecibidas"})
    private UsuariosModel messageSender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "msg_fk_usuario_destino",
        referencedColumnName = "usr_id",
        nullable = false
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "propiedades", "reservasEnviadas", "reservasRecibidas"})
    private UsuariosModel messageRecipient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "msg_fk_reserva_referencia",
        referencedColumnName = "rsrv_id",
        nullable = false
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ReservacionesModel relatedReservation;

    @Column(name = "msg_titulo_tema", length = 255)
    private String messageSubject;

    @Lob 
    @Column(name = "msg_cuerpo_contenido")
    private String messageContent;

    @Column(name = "msg_estado_lectura", nullable = false)
    private Boolean isRead = false;

    @Column(name = "msg_timestamp_envio", nullable = false, updatable = false)
    private LocalDateTime sentAtTimestamp;

    @PrePersist
    protected void onCreate() {
        sentAtTimestamp = LocalDateTime.now();
    }


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuariosModel getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(UsuariosModel messageSender) {
        this.messageSender = messageSender;
    }

    public UsuariosModel getMessageRecipient() {
        return messageRecipient;
    }

    public void setMessageRecipient(UsuariosModel messageRecipient) {
        this.messageRecipient = messageRecipient;
    }

    public ReservacionesModel getRelatedReservation() {
        return relatedReservation;
    }

    public void setRelatedReservation(ReservacionesModel relatedReservation) {
        this.relatedReservation = relatedReservation;
    }

    public String getMessageSubject() {
        return messageSubject;
    }

    public void setMessageSubject(String messageSubject) {
        this.messageSubject = messageSubject;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public LocalDateTime getSentAtTimestamp() {
        return sentAtTimestamp;
    }

    public void setSentAtTimestamp(LocalDateTime sentAtTimestamp) {
        this.sentAtTimestamp = sentAtTimestamp;
    }

    //Constructor
    public MensajesModel() {
    }

    //Constructor with parameters
    public MensajesModel(Long id, UsuariosModel messageSender, UsuariosModel messageRecipient, ReservacionesModel relatedReservation,
            String messageSubject, String messageContent, Boolean isRead, LocalDateTime sentAtTimestamp) {
        this.messageSubject = messageSubject;
        this.messageContent = messageContent;
        this.isRead = isRead;
    }   

}
