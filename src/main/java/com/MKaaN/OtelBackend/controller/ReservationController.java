package com.MKaaN.OtelBackend.controller;

import com.MKaaN.OtelBackend.entity.Reservation;
import com.MKaaN.OtelBackend.entity.Room;
import com.MKaaN.OtelBackend.entity.User;
import com.MKaaN.OtelBackend.enums.PriceCalculationRequest;
import com.MKaaN.OtelBackend.enums.ReservationStatus;
import com.MKaaN.OtelBackend.repository.UserRepository;
import com.MKaaN.OtelBackend.repository.RoomRepository;
import com.MKaaN.OtelBackend.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    // Mevcut createReservation fonksiyonu
    @PostMapping("/create")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        try {
            // Kullanıcı ID kontrolü
            if (reservation.getUser() == null || reservation.getUser().getId() == null) {
                throw new IllegalArgumentException("Invalid user ID");
            }
            // Oda ID kontrolü
            if (reservation.getRoom() == null || reservation.getRoom().getId() == null) {
                throw new IllegalArgumentException("Invalid room ID");
            }
            // Kullanıcıyı ve odayı veritabanından al
            User user = userRepository.findById(reservation.getUser().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
            Room room = roomRepository.findById(reservation.getRoom().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));

            // Rezervasyon tarihleri kontrolü
            if (reservation.getStartDate() == null || reservation.getEndDate() == null) {
                throw new IllegalArgumentException("Reservation dates must be provided");
            }

            // java.util.Date'ten LocalDate'e dönüşüm
            LocalDate resStart = reservation.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate resEnd = reservation.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Odanın müsaitlik tarihleri kontrolü (oda tarihleri LocalDate tipinde)
            if (room.getStartDate() == null || room.getEndDate() == null) {
                throw new IllegalArgumentException("Room availability dates are not set");
            }

            // Rezervasyonun oda müsaitlik aralığı içinde olması gerekiyor:
            if (resStart.isBefore(room.getStartDate())) {
                throw new IllegalArgumentException("Reservation cannot start before room's available start date: " + room.getStartDate());
            }
            if (resEnd.isAfter(room.getEndDate())) {
                throw new IllegalArgumentException("Reservation end date must be within the room's available end date: " + room.getEndDate());
            }
            if (!resEnd.isAfter(resStart)) {
                throw new IllegalArgumentException("Reservation end date must be after start date");
            }
            // (Şimdilik) Rezervasyonun başlangıç tarihi, odanın müsaitlik başlangıcına eşit olmalı.
            if (!resStart.equals(room.getStartDate())) {
                throw new IllegalArgumentException("For now, reservation must start at the room's available start date: " + room.getStartDate());
            }

            // Tüm kontroller geçtiyse, kullanıcı ve oda bilgilerini rezervasyona ata
            reservation.setUser(user);
            reservation.setRoom(room);

            // Rezervasyonu kaydet (Reservation entity'nin prePersist metodu totalPrice ve status ayarlarını yapar)
            Reservation savedReservation = reservationRepository.save(reservation);

            // Rezervasyon yapıldıktan sonra, odanın müsaitlik başlangıcını güncelle:
            // Yeni müsaitlik başlangıcı, rezervasyonun bitiş tarihi (LocalDate)
            room.setStartDate(resEnd);
            roomRepository.save(room);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedReservation);
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating reservation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // calculate-price endpoint (mevcut)
    @PostMapping("/calculate-price")
    public ResponseEntity<Double> calculatePrice(@RequestBody PriceCalculationRequest request) {
        try {
            Room room = roomRepository.findById(request.getRoomId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));

            if (request.getStartDate() == null || request.getEndDate() == null) {
                throw new IllegalArgumentException("Reservation dates must be provided");
            }

            // Tarihleri LocalDate'e dönüştürelim
            LocalDate resStart = request.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate resEnd = request.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Rezervasyon süresini gün olarak hesapla
            long daysBetween = ChronoUnit.DAYS.between(resStart, resEnd);
            if (daysBetween <= 0) {
                throw new IllegalArgumentException("Reservation must be at least one day long.");
            }

            // Toplam ücreti hesapla: gün sayısı * oda günlük fiyatı
            double totalPrice = daysBetween * room.getPrice();
            return ResponseEntity.ok(totalPrice);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        try {
            reservationRepository.deleteById(id);
            return ResponseEntity.noContent().build();  // 204 No Content
        } catch (Exception e) {
            // Hata durumunda
            System.out.println("Error deleting reservation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Admin: Tüm rezervasyonları listele
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        // Bu endpoint için, ReservationService.getAllReservations() metodunu kullanabilirsiniz.
        // Örneğin: List<Reservation> reservations = reservationService.getAllReservations();
        // Eğer ReservationService bean’ini eklemediyseniz, direkt repository üzerinden de çağırabilirsiniz.
        List<Reservation> reservations = reservationRepository.findAll();
        return ResponseEntity.ok(reservations);
    }

    // Admin: Rezervasyonu güncelle (status, tarih, adminNote)
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation updatedReservation) {
        try {
            Reservation existing = reservationRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
            existing.setStatus(updatedReservation.getStatus());
            existing.setStartDate(updatedReservation.getStartDate());
            existing.setEndDate(updatedReservation.getEndDate());
            existing.setAdminNote(updatedReservation.getAdminNote());
            existing.calculateTotalPrice();
            Reservation saved = reservationRepository.save(existing);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Admin: Rezervasyonu ödeme alarak PAID statüsüne çevir (simülasyon)
    @PutMapping("/{id}/pay")
    public ResponseEntity<Reservation> markReservationAsPaid(@PathVariable Long id) {
        try {
            Reservation existing = reservationRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
            existing.setStatus(ReservationStatus.PAID);
            Reservation saved = reservationRepository.save(existing);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}