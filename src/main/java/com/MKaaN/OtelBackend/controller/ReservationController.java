package com.MKaaN.OtelBackend.controller;

import com.MKaaN.OtelBackend.entity.Reservation;
import com.MKaaN.OtelBackend.entity.Room;
import com.MKaaN.OtelBackend.entity.User;
import com.MKaaN.OtelBackend.repository.UserRepository;
import com.MKaaN.OtelBackend.repository.RoomRepository;
import com.MKaaN.OtelBackend.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @PostMapping("/create")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        try {
            // User ID kontrolü
            if (reservation.getUser() == null || reservation.getUser().getId() == null) {
                throw new IllegalArgumentException("Invalid user ID");
            }

            // Room ID kontrolü
            if (reservation.getRoom() == null || reservation.getRoom().getId() == null) {
                throw new IllegalArgumentException("Invalid room ID");
            }

            // User'ı ve Room'u veritabanından alalım
            User user = userRepository.findById(reservation.getUser().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
            Room room = roomRepository.findById(reservation.getRoom().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));

            // Rezervasyonun ilgili alanlarını güncelle
            reservation.setUser(user);
            reservation.setRoom(room);

            // Rezervasyonu kaydet
            Reservation savedReservation = reservationRepository.save(reservation);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReservation);
        } catch (IllegalArgumentException e) {
            // Hata durumunda loglama ve uygun hata mesajı
            System.out.println("Error creating reservation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            // Genel hata durumunda loglama
            System.out.println("Unexpected error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}