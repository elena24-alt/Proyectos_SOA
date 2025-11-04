package com.example.demo.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.ReservacionesModel;
import com.example.demo.Services.ReservacionesService;



@RestController
@RequestMapping("/reservaciones")

public class ReservacionesController {

    @Autowired
    private ReservacionesService reservacionesService;


    @GetMapping()
    public List<ReservacionesModel> getAllReservaciones(){
        return reservacionesService.getAllReservaciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservacionesModel> getReservacionById(@PathVariable Long id){
        Optional<ReservacionesModel> reservacion = reservacionesService.getReservacionesById(id);
        return reservacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ReservacionesModel createReservacion(@RequestBody ReservacionesModel reservacion){
        return reservacionesService.saveReservacion(reservacion);
    }
    

    //Metodo para actualizar reservacion
    @PutMapping("/{id}")
    public ResponseEntity<ReservacionesModel> updateReservacion(@PathVariable Long id, @RequestBody ReservacionesModel reservacion){
        Optional<ReservacionesModel> existingReservacion = reservacionesService.getReservacionesById(id);

        if (existingReservacion.isPresent()) {
            ReservacionesModel reservacionToUpdate = existingReservacion.get();
            reservacionToUpdate.setFechaEntrada(reservacion.getFechaEntrada());
            reservacionToUpdate.setFechaSalida(reservacion.getFechaSalida());
            reservacionToUpdate.setNumeroHuespedes(reservacion.getNumeroHuespedes());
            reservacionToUpdate.setPrecioTotal(reservacion.getPrecioTotal());
            reservacionToUpdate.setEstado(reservacion.getEstado());
            reservacionToUpdate.setNotas(reservacion.getNotas());
            reservacionToUpdate.setCodigoReserva(reservacion.getCodigoReserva());
           

            ReservacionesModel updatedReservacion = reservacionesService.saveReservacion(reservacionToUpdate);
            return ResponseEntity.ok(updatedReservacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Delete reservacion
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservacion(@PathVariable Long id){
        Optional<ReservacionesModel> existingReservacion = reservacionesService.getReservacionesById(id);

        if (existingReservacion.isPresent()) {
            reservacionesService.deleteReservacion(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    



    }
    
    
