package com.example.demo.Controllers;

import java.time.LocalDate;
import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.ReservacionesModel;
import com.example.demo.Models.ReservacionesModel.BookingStatus;
import com.example.demo.Services.ReservacionesService;

@RestController
@RequestMapping("/api/v1/bookings/reservations")
public class ReservacionesController {
    @Autowired    
    private ReservacionesService bookingDataService;

    //Method to retrieve all bookings
    @GetMapping()
    public List<ReservacionesModel> fetchAllBookings(){
        return bookingDataService.fetchAllBookings();
    }

    //Method to retrieve booking by id
    @GetMapping("/{id}")
    public ResponseEntity<ReservacionesModel> findBookingById(@PathVariable Long id){
        Optional<ReservacionesModel> booking = bookingDataService.findBookingById(id);
        return booking.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Method to update booking
    @PutMapping("/{id}")
    public ResponseEntity<ReservacionesModel> modifyBookingRecord(@PathVariable Long id, @RequestBody ReservacionesModel booking){
        Optional<ReservacionesModel> existingBooking = bookingDataService.findBookingById(id);

        if (existingBooking.isPresent()) {
            ReservacionesModel bookingToUpdate = existingBooking.get();
            bookingToUpdate.setCheckInDate(booking.getCheckInDate());
            bookingToUpdate.setCheckOutDate(booking.getCheckOutDate());
            bookingToUpdate.setTotalGuestsCount(booking.getTotalGuestsCount());
            bookingToUpdate.setTotalReservationAmount(booking.getTotalReservationAmount());
            bookingToUpdate.setCurrentBookingStatus(booking.getCurrentBookingStatus());
            bookingToUpdate.setSpecialGuestNotes(booking.getSpecialGuestNotes());
            bookingToUpdate.setConfirmationCode(booking.getConfirmationCode());
           

            ReservacionesModel updatedBooking = bookingDataService.persistBookingData(bookingToUpdate);
            return ResponseEntity.ok(updatedBooking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Delete booking
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeBookingById(@PathVariable Long id){
        Optional<ReservacionesModel> existingBooking = bookingDataService.findBookingById(id);

        if (existingBooking.isPresent()) {
            bookingDataService.removeBookingById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Endpoint to create a new booking with business logic
    @PostMapping
    public ResponseEntity<ReservacionesModel> registerNewBooking(@RequestBody ReservacionesModel booking) {
        
        try {
            ReservacionesModel newBooking = bookingDataService.initializeApartmentBooking(booking);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(newBooking);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint to cancel a booking
    @PutMapping("/{id}/cancel")
    public ResponseEntity<ReservacionesModel> cancelBookingRequest(@PathVariable Long id) {
        
        Optional<ReservacionesModel> cancelledBooking = bookingDataService.cancelBookingRequest(id);
        
        return cancelledBooking.map(ResponseEntity::ok) // Returns 200 OK with the cancelled object
                               .orElseGet(() -> ResponseEntity.notFound().build()); // Returns 404 Not Found
    }

    //Method to retrieve bookings by guest user
    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<ReservacionesModel>> fetchBookingsByGuestUser(@PathVariable Long guestId) {
        
        List<ReservacionesModel> bookings = bookingDataService.fetchBookingsByGuestUser(guestId);
        
        if (bookings.isEmpty()) {
            // Returns 404 Not Found if the guest does not exist or has no bookings
            return ResponseEntity.notFound().build();
        }
        
        // Returns 200 OK with the list of bookings (which may be empty if the guest exists)
        return ResponseEntity.ok(bookings); 
    }

    //Method to retrieve bookings by date
    //URL: GET http://localhost:10101/api/v1/bookings/reservations/date?date=YYYY-MM-DD
    @GetMapping("/date")
    public ResponseEntity<List<ReservacionesModel>> fetchBookingsByDate(@RequestParam LocalDate date) {
        
        List<ReservacionesModel> bookings = bookingDataService.fetchBookingsByDate(date);
        
        return ResponseEntity.ok(bookings); 
    }

    //Method to retrieve bookings by status
    //URL: GET http://localhost:10101/api/v1/bookings/reservations/status?status=STATUS

    @GetMapping("/status")
    public ResponseEntity<List<ReservacionesModel>> fetchBookingsByStatus(@RequestParam BookingStatus status) {
        
        List<ReservacionesModel> bookings = bookingDataService.fetchBookingsByStatus(status);
        
        // Returns 200 OK with the list of bookings found (may be empty).
        return ResponseEntity.ok(bookings); 
    }

    //Method to retrieve bookings by guest user and status
    //http://localhost:10101/api/v1/bookings/reservations/guest/5/status?status=CONFIRMED
    @GetMapping("/guest/{guestId}/status")
    public ResponseEntity<List<ReservacionesModel>> fetchBookingsByGuestUserAndStatus(
            @PathVariable Long guestId, 
            @RequestParam BookingStatus status) {
        
        List<ReservacionesModel> bookings = bookingDataService.fetchBookingsByGuestUserAndStatus(guestId, status);
        
        // Returns 200 OK with the list of bookings found (may be empty).
        return ResponseEntity.ok(bookings); 
    }

    //Method to retrieve bookings by property and status
    //http://localhost:10101/api/v1/bookings/reservations/property/3/status?status=CONFIRMED
    @GetMapping("/property/{propertyId}/status")
    public ResponseEntity<List<ReservacionesModel>> fetchBookingsByPropertyAndStatus(
            @PathVariable Long propertyId, 
            @RequestParam BookingStatus status) {
        
        List<ReservacionesModel> bookings = bookingDataService.fetchBookingsByPropertyAndStatus(propertyId, status);
        
        // Returns 200 OK with the list of bookings found (may be empty if the property exists).
        return ResponseEntity.ok(bookings); 
    }
    
    //Method to retrieve bookings by guest user and date
    //http://localhost:10101/api/v1/bookings/reservations/guest/5/date?date=2024-07-15
    @GetMapping("/guest/{guestId}/date")
    public ResponseEntity<List<ReservacionesModel>> fetchBookingsByGuestUserAndDate(
            @PathVariable Long guestId, 
            @RequestParam LocalDate date) {
        
        List<ReservacionesModel> bookings = bookingDataService.fetchBookingsByGuestUserAndDate(guestId, date);
        
        // Returns 200 OK with the list of bookings found (may be empty).
        return ResponseEntity.ok(bookings); 
    }

    //Method to retrieve bookings by property and date
    //http://localhost:10101/api/v1/bookings/reservations/property/3/date?date=2024-07-15
    @GetMapping("/property/{propertyId}/date")
    public ResponseEntity<List<ReservacionesModel>> fetchBookingsByPropertyAndDate(
            @PathVariable Long propertyId, 
            @RequestParam LocalDate date) {
        
        List<ReservacionesModel> bookings = bookingDataService.fetchBookingsByPropertyAndDate(propertyId, date);
        
        // Returns 200 OK with the list of bookings found (may be empty).
        return ResponseEntity.ok(bookings); 
    }

    //Method to cancel all bookings by guest user
    //http://localhost:10101/api/v1/bookings/reservations/guest/5/cancel-all
    @PutMapping("/guest/{guestId}/cancel-all")
    public ResponseEntity<List<ReservacionesModel>> cancelAllGuestUserBookings(@PathVariable Long guestId) {
        
        try {
            List<ReservacionesModel> cancelledBookings = 
                bookingDataService.cancelAllGuestUserBookings(guestId);
            
            if (cancelledBookings.isEmpty()) {
                // If the guest exists but has no bookings, returns OK with empty list
                return ResponseEntity.ok(cancelledBookings); 
            }
            
            return ResponseEntity.ok(cancelledBookings); // 200 OK with the list of cancelled bookings
            
        } catch (RuntimeException e) {
            // Catches the exception if the guest was not found
            if (e.getMessage().contains("Guest user not found")) {
                return ResponseEntity.notFound().build();
            }
            throw e; // Re-throws other errors
        }
    }

    //Method to cancel all bookings by property
    //http://localhost:10101/api/v1/bookings/reservations/property/3/cancel-all
    @PutMapping("/property/{propertyId}/cancel-all")
    public ResponseEntity<List<ReservacionesModel>> cancelAllPropertyBookings(@PathVariable Long propertyId) {
        
        try {
            List<ReservacionesModel> cancelledBookings = 
                bookingDataService.cancelAllPropertyBookings(propertyId);
            
            if (cancelledBookings.isEmpty()) {
                // If the property exists but has no active bookings, returns OK with empty list
                return ResponseEntity.ok(cancelledBookings); 
            }
            
            return ResponseEntity.ok(cancelledBookings); // 200 OK with the list of cancelled bookings
            
        } catch (RuntimeException e) {
            // Catches the exception if the property was not found
            if (e.getMessage().contains("Property not found")) {
                return ResponseEntity.notFound().build();
            }
            throw e; // Re-throws other errors
        }
    }

    //Method to cancel all bookings by date
    //URL: PUT http://localhost:10101/api/v1/bookings/reservations/cancel-by-date?date=YYYY-MM-DD
    @PutMapping("/cancel-by-date")
    public ResponseEntity<List<ReservacionesModel>> cancelBookingsByDate(@RequestParam LocalDate date) {
        
        List<ReservacionesModel> cancelledBookings = 
             bookingDataService.cancelBookingsByDate(date);
        
        if (cancelledBookings.isEmpty()) {
            // No active bookings found to cancel on that date.
            return ResponseEntity.ok(cancelledBookings); 
        }
        
        return ResponseEntity.ok(cancelledBookings); // 200 OK with the list of bookings that were cancelled
    }

}
