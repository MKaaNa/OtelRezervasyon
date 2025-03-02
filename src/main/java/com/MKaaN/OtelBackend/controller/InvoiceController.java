package com.MKaaN.OtelBackend.controller;

import com.MKaaN.OtelBackend.entity.Reservation;
import com.MKaaN.OtelBackend.repository.ReservationRepository;
import com.MKaaN.OtelBackend.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ReservationRepository reservationRepository;

    /**
     * Belirtilen rezervasyon ID'si için fatura PDF dosyası oluşturur.
     *
     * @param reservationId Fatura oluşturulacak rezervasyonun ID'si
     * @return PDF içeriğini döndürür
     */
    @GetMapping(value = "/invoice/{reservationId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getInvoicePdf(@PathVariable Long reservationId) {
        try {
            System.out.println("Generating invoice for reservation ID: " + reservationId);

            // Reservation kontrolü
            Reservation reservation = reservationRepository.findById(reservationId)
                    .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID: " + reservationId));

            byte[] pdfBytes = invoiceService.generateInvoicePdf(reservationId);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice_" + reservationId + ".pdf")
                    .body(pdfBytes);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid reservation ID: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            System.err.println("Unexpected error generating invoice: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}