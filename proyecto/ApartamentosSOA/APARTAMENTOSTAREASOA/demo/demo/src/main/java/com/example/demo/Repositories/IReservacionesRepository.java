package com.example.demo.Repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.PropiedadesModel;
import com.example.demo.Models.ReservacionesModel;
import com.example.demo.Models.ReservacionesModel.BookingStatus;
import com.example.demo.Models.UsuariosModel;

@Repository
public interface IReservacionesRepository extends JpaRepository<ReservacionesModel, Long> {
    
    //Metodo para encontrar reservacion por usuario
    List<ReservacionesModel> findByGuestUser(UsuariosModel guestUser);

    //Metodo para encontrar reservacion por propiedad
    List<ReservacionesModel> findByPropertyBooked(PropiedadesModel propertyBooked);

    //Metodo para encontrar reservacion por fecha
    List<ReservacionesModel> findByCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(
            LocalDate startDate, 
            LocalDate endDate
    );

    //Metodo para encontrar reservacion por estado
    List<ReservacionesModel> findByCurrentBookingStatus(ReservacionesModel.BookingStatus bookingStatus);

    //Crear un método para obtener las reservaciones por usuario y estado
    List<ReservacionesModel> findByGuestUserAndCurrentBookingStatus(UsuariosModel guestUser, ReservacionesModel.BookingStatus bookingStatus);

    //Crear un método para obtener las reservaciones por propiedad y estado
    List<ReservacionesModel> findByPropertyBookedAndCurrentBookingStatus(PropiedadesModel propertyBooked, ReservacionesModel.BookingStatus bookingStatus);

    //Crear un método para obtener las reservaciones por usuario y fecha
    List<ReservacionesModel> findByGuestUserAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(
            UsuariosModel guestUser, 
            LocalDate startDate, 
            LocalDate endDate
    );
    
    //Metodo para obtener las reservaciones por propiedad y fecha
    List<ReservacionesModel> findByPropertyBookedAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(
            PropiedadesModel propertyBooked, 
            LocalDate startDate, 
            LocalDate endDate
    );

}
