package com.example.demo.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.PropiedadesModel;
import com.example.demo.Models.ReservacionesModel;
import com.example.demo.Models.ReservacionesModel.BookingStatus;
import com.example.demo.Models.UsuariosModel;
import com.example.demo.Repositories.IReservacionesRepository;

import jakarta.transaction.Transactional;

@Service
public class ReservacionesService {
    
    @Autowired
    private IReservacionesRepository bookingDataRepository;

    @Autowired
    private UsuariosService userManagementService;

    @Autowired
    private PropiedadesService propertyManagementService;


    public List<ReservacionesModel> fetchAllBookings() {
        return bookingDataRepository.findAll();
    }

    public Optional<ReservacionesModel> findBookingById(Long id) {
        return bookingDataRepository.findById(id);
    }

    public ReservacionesModel persistBookingData(ReservacionesModel bookingData) {
        return bookingDataRepository.save(bookingData);
    }

    public void removeBookingById(Long id) {
        bookingDataRepository.deleteById(id);
    }

    // Método para reservar un apartamento
    public ReservacionesModel createNewBooking(ReservacionesModel newBooking) {
        
        
        if (newBooking.getCurrentBookingStatus() == null) {
            newBooking.setCurrentBookingStatus(ReservacionesModel.BookingStatus.WAITING_CONFIRMATION);
        }
        
        if (newBooking.getCreatedAtTimestamp() == null) {
            newBooking.setCreatedAtTimestamp(LocalDateTime.now());
        }
        
       
        return bookingDataRepository.save(newBooking);
    }

    // Alias method for Controller compatibility
    public ReservacionesModel initializeApartmentBooking(ReservacionesModel newBooking) {
        return createNewBooking(newBooking);
    }

    //Metodo para cancelar una reserva
    public Optional<ReservacionesModel> cancelBookingRequest(Long bookingId) {
        
        Optional<ReservacionesModel> bookingOptional = bookingDataRepository.findById(bookingId);
        
        if (bookingOptional.isPresent()) {
            ReservacionesModel bookingToCancel = bookingOptional.get();
            
            // 1. Verificar el estado actual (lógica de negocio)
            if (bookingToCancel.getCurrentBookingStatus() == ReservacionesModel.BookingStatus.BOOKING_CANCELLED || 
                bookingToCancel.getCurrentBookingStatus() == ReservacionesModel.BookingStatus.COMPLETED_CHECKOUT) {
                // Aquí podrías lanzar una excepción si no se permite cancelar
                // una reserva ya cancelada o completada.
            }
            
            // 2. Actualizar el estado a CANCELADA
            bookingToCancel.setCurrentBookingStatus(ReservacionesModel.BookingStatus.BOOKING_CANCELLED);
            
            // 3. Guardar la entidad actualizada
            return Optional.of(bookingDataRepository.save(bookingToCancel));
        }
        
        return Optional.empty(); // Reserva no encontrada
    }

    //Metodo para obtener reservas por usuario
    public List<ReservacionesModel> fetchBookingsByGuestUser(Long userId) {
        
        // 1. Buscar la entidad del cliente (Usuario)
        Optional<UsuariosModel> userOptional = userManagementService.findUserById(userId);

        if (userOptional.isPresent()) {
            // 2. Si existe, usar el repositorio para filtrar por el objeto cliente
            UsuariosModel guestUser = userOptional.get();
            return bookingDataRepository.findByGuestUser(guestUser);
        }
        
        // 3. Si el cliente no existe, devuelve una lista vacía
        return Collections.emptyList();
    }

    //Metodo para obtener reservas por fecha
    public List<ReservacionesModel> fetchBookingsByDate(LocalDate searchDate) {
        
        return bookingDataRepository.findByCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(
                searchDate, 
                searchDate // Usamos la misma fecha para ambos parámetros
        );
    }

    //Metodo para obtener reservas por estado
    public List<ReservacionesModel> fetchBookingsByStatus(ReservacionesModel.BookingStatus bookingStatus) {
        return bookingDataRepository.findByCurrentBookingStatus(bookingStatus);
    }

    //Método para obtener reservaciones por usuario y estado
    public List<ReservacionesModel> fetchBookingsByGuestUserAndStatus(Long userId, ReservacionesModel.BookingStatus bookingStatus) {
        
        // 1. Buscar la entidad del cliente (Usuario)
        Optional<UsuariosModel> userOptional = userManagementService.findUserById(userId);

        if (userOptional.isPresent()) {
            // 2. Si existe, usar el repositorio para filtrar por cliente y estado
            UsuariosModel guestUser = userOptional.get();
            return bookingDataRepository.findByGuestUserAndCurrentBookingStatus(guestUser, bookingStatus);
        }
        
        // 3. Si el cliente no existe, devuelve una lista vacía
        return Collections.emptyList();
    }

    //Método para obtener reservaciones por propiedad y estado
    public List<ReservacionesModel> fetchBookingsByPropertyAndStatus(Long propertyId, ReservacionesModel.BookingStatus bookingStatus) {
        
        // 1. Buscar la entidad de la propiedad
        Optional<PropiedadesModel> propertyOptional = propertyManagementService.findPropertyById(propertyId);

        if (propertyOptional.isPresent()) {
            // 2. Si existe, usar el repositorio para filtrar por propiedad y estado
            PropiedadesModel property = propertyOptional.get();
            return bookingDataRepository.findByPropertyBookedAndCurrentBookingStatus(property, bookingStatus);
        }
        
        // 3. Si la propiedad no existe, devuelve una lista vacía
        return Collections.emptyList();
    }

    //Método para obtener reservaciones por usuario y fecha
    public List<ReservacionesModel> fetchBookingsByGuestUserAndDate(Long userId, LocalDate searchDate) {
        
        // 1. Buscar la entidad del cliente (Usuario)
        Optional<UsuariosModel> userOptional = userManagementService.findUserById(userId);

        if (userOptional.isPresent()) {
            UsuariosModel guestUser = userOptional.get();
            
            // 2. Filtrar por cliente y por traslape con la fecha de búsqueda
            return bookingDataRepository.findByGuestUserAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(
                guestUser, 
                searchDate, 
                searchDate
            );
        }
        
        // 3. Si el cliente no existe, devuelve una lista vacía
        return Collections.emptyList();
    }

    //Método para obtener reservaciones por propiedad y fecha
    public List<ReservacionesModel> fetchBookingsByPropertyAndDate(Long propertyId, LocalDate searchDate) {
        
        // 1. Buscar la entidad de la propiedad
        Optional<PropiedadesModel> propertyOptional = propertyManagementService.findPropertyById(propertyId);

        if (propertyOptional.isPresent()) {
            PropiedadesModel property = propertyOptional.get();
            
            // 2. Filtrar por propiedad y por traslape con la fecha de búsqueda
            return bookingDataRepository.findByPropertyBookedAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(
                property, 
                searchDate, 
                searchDate
            );
        }
        
        // 3. Si la propiedad no existe, devuelve una lista vacía
        return Collections.emptyList();
    }

    //Método para cancelar todas las reservas por usuario
    public List<ReservacionesModel> cancelAllGuestUserBookings(Long userId) {
        
        // 1. Verificar si el usuario existe
        Optional<UsuariosModel> userOptional = userManagementService.findUserById(userId);

        if (!userOptional.isPresent()) {
            // Si el usuario no existe, se lanza una excepción o se devuelve null/lista vacía.
            throw new RuntimeException("Guest user not found with ID: " + userId);
        }
        
        UsuariosModel guestUser = userOptional.get();
        
        // 2. Obtener todas las reservas activas del cliente (usando el método findByGuestUser)
        List<ReservacionesModel> guestBookings = bookingDataRepository.findByGuestUser(guestUser);

        // 3. Iterar y actualizar el estado
        for (ReservacionesModel booking : guestBookings) {
            // Se puede añadir lógica aquí para no cancelar reservas ya completadas, etc.
            if (booking.getCurrentBookingStatus() != ReservacionesModel.BookingStatus.COMPLETED_CHECKOUT) { 
                booking.setCurrentBookingStatus(ReservacionesModel.BookingStatus.BOOKING_CANCELLED);
            }
        }
        
        // 4. Guardar las reservas actualizadas masivamente
        return bookingDataRepository.saveAll(guestBookings);
    }

    //Metodo para cancelar todas las reservas por propiedad
    public List<ReservacionesModel> cancelAllPropertyBookings(Long propertyId) {
        
        // 1. Verificar si la propiedad existe
        Optional<PropiedadesModel> propertyOptional = propertyManagementService.findPropertyById(propertyId);

        if (!propertyOptional.isPresent()) {
            // Si la propiedad no existe, se lanza una excepción
            throw new RuntimeException("Property not found with ID: " + propertyId);
        }
        
        PropiedadesModel property = propertyOptional.get();
        
        // 2. Obtener todas las reservas del apartamento (usando findByPropertyBooked)
        List<ReservacionesModel> propertyBookings = bookingDataRepository.findByPropertyBooked(property);

        // 3. Iterar y actualizar el estado
        for (ReservacionesModel booking : propertyBookings) {
            // Solo cancelamos si no están ya completadas o canceladas
            if (booking.getCurrentBookingStatus() != ReservacionesModel.BookingStatus.COMPLETED_CHECKOUT && booking.getCurrentBookingStatus() != ReservacionesModel.BookingStatus.BOOKING_CANCELLED) { 
                booking.setCurrentBookingStatus(ReservacionesModel.BookingStatus.BOOKING_CANCELLED);
            }
        }
        
        // 4. Guardar las reservas actualizadas masivamente
        return bookingDataRepository.saveAll(propertyBookings);
    }

    //Metodo para cancelar todas las reservas por fecha
    @Transactional // Asegura que todas las actualizaciones se realicen como una sola transacción
    public List<ReservacionesModel> cancelBookingsByDate(LocalDate searchDate) {
        
        // 1. Obtener todas las reservas que coinciden o se traslapan con la fecha
        List<ReservacionesModel> activeBookingsOnDate = 
            bookingDataRepository.findByCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(
                searchDate, 
                searchDate
            );

        // 2. Iterar y actualizar el estado
        for (ReservacionesModel booking : activeBookingsOnDate) {
            // Solo cancelar si no están ya canceladas o completadas
            if (booking.getCurrentBookingStatus() != ReservacionesModel.BookingStatus.COMPLETED_CHECKOUT && booking.getCurrentBookingStatus() != ReservacionesModel.BookingStatus.BOOKING_CANCELLED) {
                booking.setCurrentBookingStatus(ReservacionesModel.BookingStatus.BOOKING_CANCELLED);
            }
        }
        
        // 3. Guardar todas las reservas actualizadas masivamente
        return bookingDataRepository.saveAll(activeBookingsOnDate);
    }
}
