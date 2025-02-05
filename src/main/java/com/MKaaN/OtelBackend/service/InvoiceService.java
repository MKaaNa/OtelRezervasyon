package com.MKaaN.OtelBackend.service;

import com.MKaaN.OtelBackend.entity.Reservation;
import com.MKaaN.OtelBackend.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class InvoiceService {

    @Autowired
    private ReservationRepository reservationRepository;

    /**
     * Belirtilen rezervasyon ID'si için basit bir TXT fatura dosyası oluşturur.
     *
     * @param reservationId Fatura oluşturulacak rezervasyonun ID'si
     * @return Fatura metnini byte array olarak döndürür.
     * @throws Exception Oluşabilecek hatalar
     */
    public byte[] generateInvoiceTxt(Long reservationId) throws Exception {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        StringBuilder sb = new StringBuilder();
        sb.append("Invoice for Reservation ID: ").append(reservationId).append("\n");
        sb.append("User Email: ").append(reservation.getUser().getEmail()).append("\n");
        sb.append("Room Type: ").append(reservation.getRoom().getRoomType()).append("\n");
        sb.append("Reservation Dates: ").append(reservation.getStartDate())
                .append(" - ").append(reservation.getEndDate()).append("\n");
        sb.append("Total Price: ").append(reservation.getTotalPrice()).append("\n");
        // İsteğe bağlı ek detaylar eklenebilir...
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Ödeme sonrası basit Invoice DTO üretir.
     *
     * @param reservation Ödeme yapılan rezervasyon
     * @return Invoice DTO
     */
    public Invoice generateInvoice(Reservation reservation) {
        return new Invoice(reservation.getId());
    }
}