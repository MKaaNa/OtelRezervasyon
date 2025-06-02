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

    // createReservation, calculatePrice, deleteReservation, getAllReservations, updateReservation endpoint'leri mevcut.
    @PostMapping("/create")
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
        if (reservation.getUser() == null || reservation.getUser().getId() == null) {
            throw new IllegalArgumentException("Geçersiz kullanıcı ID'si.");
        }
        if (reservation.getRoom() == null || reservation.getRoom().getId() == null) {
            throw new IllegalArgumentException("Geçersiz oda ID'si.");
        }
        User user = userRepository.findById(reservation.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı. Lütfen kullanıcı veritabanında mevcut mu kontrol edin."));
        reservation.setUser(user);

        Room room = roomRepository.findById(reservation.getRoom().getId())
                .orElseThrow(() -> new IllegalArgumentException("Oda bulunamadı. Lütfen oda veritabanında mevcut mu kontrol edin."));
        reservation.setRoom(room);

        // Oda ve tarih aralığı çakışma kontrolü
        if (reservationRepository.existsByRoomIdAndDateRange(
                reservation.getRoom().getId(),
                reservation.getStartDate(),
                reservation.getEndDate())) {
            throw new IllegalArgumentException("Bu oda, seçilen tarihlerde zaten rezerve edilmiş.");
        }

        reservation.calculateTotalPrice();
        reservation.setStatus(ReservationStatus.PENDING);

        Reservation savedReservation = reservationRepository.save(reservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReservation);
    }

    @PostMapping("/calculate-price")
    public ResponseEntity<Double> calculatePrice(@RequestBody PriceCalculationRequest request) {
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
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return ResponseEntity.ok(reservations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation updatedReservation) {
        Reservation existing = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        existing.setStatus(updatedReservation.getStatus());
        existing.setStartDate(updatedReservation.getStartDate());
        existing.setEndDate(updatedReservation.getEndDate());
        existing.setAdminNote(updatedReservation.getAdminNote());
        existing.calculateTotalPrice();
        Reservation saved = reservationRepository.save(existing);
        return ResponseEntity.ok(saved);
    }

    @PutMapping(value = "/{id}/pay", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> markReservationAsPaid(@PathVariable Long id) throws Exception {
        Reservation existing = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        // Sadece APPROVED olan rezervasyonlar için ödeme yapılabilir
        if (existing.getStatus() != ReservationStatus.APPROVED) {
            throw new IllegalArgumentException("Sadece onaylanmış rezervasyonlar için ödeme yapılabilir.");
        }

        // Ödeme simülasyonu -> Rezervasyon PAID olarak işaretleniyor
        existing.setStatus(ReservationStatus.PAID);
        reservationRepository.save(existing);

        // **Fatura PDF oluştur**
        byte[] invoicePdf = invoiceService.generateInvoice(existing);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice_" + id + ".pdf")
                .body(invoicePdf);
    }

    // PDF fatura dosyasını döndüren endpoint
    @GetMapping(value = "/invoice/{reservationId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getInvoicePdf(@PathVariable Long reservationId) throws Exception {
        byte[] pdfBytes = invoiceService.generateInvoicePdf(reservationId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice_" + reservationId + ".pdf")
                .body(pdfBytes);
    }
}
