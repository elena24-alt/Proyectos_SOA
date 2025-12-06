package com.example.demo.Models;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_usuarios_v2")
public class UsuariosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "usr_tipo_perfil", nullable = false,length = 20)
    private TipoCliente userProfileType;

    @Column(name = "usr_nombre_completo", nullable = false, length = 35)
    private String fullName;

    @Column(name = "usr_apellido_paterno", nullable = false, length = 35)
    private String fatherName;

    @Column(name = "usr_apellido_materno", nullable = false, length = 35)
    private String motherName;

    @Column(name = "usr_correo_electronico", unique = true, nullable = false, length = 50)
    private String emailAddress;

    @Column(name = "usr_contacto_movil", length = 15)
    private String mobilePhone;

    @Column(name = "usr_fecha_nacimiento")
    private Date birthDate;

    @Column(name = "usr_numero_identificacion", unique = true, length = 20)
    private String identificationNumber;

    @Column(name = "usr_domicilio_principal", length = 100)
    private String homeAddress;

    @Column(name = "usr_timestamp_creacion", nullable = false)
    private LocalDateTime createdAtTimestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "usr_estado_vigencia", nullable = false)
    private Status accountStatus;

    @Column(name = "usr_contrasenia_encriptada", nullable = false, length = 64)
    private String encryptedPassword;

    @Column(name = "usr_timestamp_ultimo_login", nullable = false)
    private LocalDateTime lastLoginTimestamp;

    private enum TipoCliente {
        PROPIETARIO,
        INQUILINO,
        HUESPED
    }  
    
    private enum Status{
        ACTIVO,
        INACTIVO,
        SUSPENDIDO
    }

    // Getters and Setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoCliente getUserProfileType() {
        return userProfileType;
    }

    public void setUserProfileType(TipoCliente userProfileType) {
        this.userProfileType = userProfileType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public LocalDateTime getCreatedAtTimestamp() {
        return createdAtTimestamp;
    }

    public void setCreatedAtTimestamp(LocalDateTime createdAtTimestamp) {
        this.createdAtTimestamp = createdAtTimestamp;
    }

    public Status getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Status accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public LocalDateTime getLastLoginTimestamp() {
        return lastLoginTimestamp;
    }

    public void setLastLoginTimestamp(LocalDateTime lastLoginTimestamp) {
        this.lastLoginTimestamp = lastLoginTimestamp;
    }

    @PrePersist
    protected void onCreate() {
        createdAtTimestamp = LocalDateTime.now();
        lastLoginTimestamp = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "messageSender", cascade = CascadeType.ALL)
    private List<MensajesModel> sentMessages;

    @OneToMany(mappedBy = "messageRecipient", cascade = CascadeType.ALL)
    private List<MensajesModel> receivedMessages;

    //Constructors

    public UsuariosModel() {
    }

    // Constructor with all fields except id, createdAtTimestamp, and lastLoginTimestamp
    public UsuariosModel(TipoCliente userProfileType, String fullName, String fatherName, String motherName,
            String emailAddress, String mobilePhone, Date birthDate, String identificationNumber, String homeAddress, Status accountStatus,
            String encryptedPassword) {
        this.userProfileType = userProfileType;
        this.fullName = fullName;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.emailAddress = emailAddress;
        this.mobilePhone = mobilePhone;
        this.birthDate = birthDate;
        this.identificationNumber = identificationNumber;
        this.homeAddress = homeAddress;
        this.accountStatus = accountStatus;
        this.encryptedPassword = encryptedPassword;
    }
}
