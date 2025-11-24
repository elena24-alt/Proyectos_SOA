package com.iotfitness.usuarios.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sesiones_entrenamiento") 
public class SesionEntrenamiento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    @ManyToOne 
    @JoinColumn(name = "user_id", nullable = false) 
    private UsuariosModel usuario; 
    
    private String tipo; 
    private int duracionMinutos;
    private int caloriasQuemadas;

 
    public SesionEntrenamiento() {}
    

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

   
    public UsuariosModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosModel usuario) {
        this.usuario = usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public int getCaloriasQuemadas() {
        return caloriasQuemadas;
    }

    public void setCaloriasQuemadas(int caloriasQuemadas) {
        this.caloriasQuemadas = caloriasQuemadas;
    }
}