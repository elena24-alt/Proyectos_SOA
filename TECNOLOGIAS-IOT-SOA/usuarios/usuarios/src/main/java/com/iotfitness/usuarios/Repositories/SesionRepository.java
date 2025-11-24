package com.iotfitness.usuarios.Repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iotfitness.usuarios.Models.SesionEntrenamiento;

@Repository
public interface SesionRepository extends JpaRepository<SesionEntrenamiento, Long> {
    // Método personalizado de Spring Data JPA para buscar por FK (user_id)
    List<SesionEntrenamiento> findByUsuarioId(Long usuarioId);
}