package com.iotfitness.usuarios.Repositories;

import org.springframework.data.jpa.repository.JpaRepository; // <-- ¡Importación Ajustada!
import org.springframework.stereotype.Repository;

import com.iotfitness.usuarios.Models.UsuariosModel;


@Repository
public interface UserRepository extends JpaRepository<UsuariosModel, Long> {
   
}