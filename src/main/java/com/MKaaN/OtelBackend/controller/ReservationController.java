package com.MKaaN.OtelBackend.controller;

import com.MKaaN.OtelBackend.entity.Reservation;
import com.MKaaN.OtelBackend.entity.Room;
import com.MKaaN.OtelBackend.entity.User;
import com.MKaaN.OtelBackend.enums.PriceCalculationRequest;
import com.MKaaN.OtelBackend.enums.ReservationStatus;
import com.MKaaN.OtelBackend.repository.UserRepository;
import com.MKaaN.OtelBackend.repository.RoomRepository;
import com.MKaaN.OtelBackend.repository.ReservationRepository;
import com.MKaaN.OtelBackend.service.InvoiceService;
import com.MKaaN.OtelBackend.service.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @Autowired
    private InvoiceService invoiceService;

    // createReservation, calculatePrice, deleteReservation, getAllReservations, updateReservation endpoint’leri mevcut.
    // Örneğin, createReservation aşağıdaki gibi:
    @PostMapping("/create")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        try {
            if (reservation.getUser() == null || reservation.getUser().getId() == null) {
                throw new IllegalArgumentException("Invalid user ID");
            }
            if (reservation.getRoom() == null || reservation.getRoom().getId() == null) {
                throw new IllegalArgumentException("Invalid room ID");
            }
            User user = userRepository.findById(reservation.getUser().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
            Room room = roomRepository.findById(reservation.getRoom().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));
            if (reservation.getStartDate() == null || reservation.getEndDate() == null) {
                throw new IllegalArgumentException("Reservation dates must be provided");
            }
            LocalDate resStart = reservation.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate resEnd = reservation.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (room.getStartDate() == null || room.getEndDate() == null) {
                throw new IllegalArgumentException("Room availability dates are not set");
            }
            if (resStart.isBefore(room.getStartDate())) {
                throw new IllegalArgumentException("Reservation cannot start before room's available start date: " + room.getStartDate());
            }
            if (resEnd.isAfter(room.getEndDate())) {
                throw new IllegalArgumentException("Reservation end date must be within the room's available end date: " + room.getEndDate());
            }
            if (!resEnd.isAfter(resStart)) {
                throw new IllegalArgumentException("Reservation end date must be after start date");
            }
            if (!resStart.equals(room.getStartDate())) {
                throw new IllegalArgumentException("For now, reservation must start at the room's available start date: " + room.getStartDate());
            }
            reservation.setUser(user);
            reservation.setRoom(room);
            Reservation savedReservation = reservationRepository.save(reservation);
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

    @PostMapping("/calculate-price")
    public ResponseEntity<Double> calculatePrice(@RequestBody PriceCalculationRequest request) {
        try {
            Room room = roomRepository.findById(request.getRoomId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));
            if (request.getStartDate() == null || request.getEndDate() == null) {
                throw new IllegalArgumentException("Reservation dates must be provided");
            }
            LocalDate resStart = request.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate resEnd = request.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            long daysBetween = ChronoUnit.DAYS.between(resStart, resEnd);
            if (daysBetween <= 0) {
                throw new IllegalArgumentException("Reservation must be at least one day long.");
            }
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
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.out.println("Error deleting reservation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return ResponseEntity.ok(reservations);
    }

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

    // Ödeme endpoint: Rezervasyonu PAID yapar ve basit Invoice DTO döndürür.
    @PutMapping(value = "/{id}/pay", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Invoice> markReservationAsPaid(@PathVariable Long id) {
        try {
            Reservation existing = reservationRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

            if (existing.getStatus() != ReservationStatus.APPROVED) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            existing.setStatus(ReservationStatus.PAID);
            Reservation saved = reservationRepository.save(existing);

            Invoice invoice = invoiceService.generateInvoice(saved);
            return ResponseEntity.ok(invoice);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Fatura TXT dosyasını döndüren endpoint
    @GetMapping(value = "/invoice/{reservationId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<byte[]> getInvoiceTxt(@PathVariable Long reservationId) {
        try {
            byte[] txtBytes = invoiceService.generateInvoiceTxt(reservationId);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice_" + reservationId + ".txt")
                    .body(txtBytes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}