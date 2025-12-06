package com.example.demo.Repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.DisponibilidadModel;
import com.example.demo.Models.PropiedadesModel;

@Repository
public interface IDisponibilidadRepository extends JpaRepository<DisponibilidadModel, Long> {

    @Query("SELECT d FROM DisponibilidadModel d WHERE " +
           "d.propertyReference = :propertyRef AND " +
           "d.calendarDate BETWEEN :startDate AND :endDate AND " +
           "d.isAvailable = TRUE")
    List<DisponibilidadModel> findAvailableDatesForProperty(
            @Param("propertyRef") PropiedadesModel propertyRef,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
    
}
