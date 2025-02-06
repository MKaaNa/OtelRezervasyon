package com.MKaaN.OtelBackend.service;

import com.MKaaN.OtelBackend.entity.Reservation;
import com.MKaaN.OtelBackend.repository.ReservationRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private ReservationRepository reservationRepository;

    private static final String LOGO_PATH = "logo.png";  // Classpath içindeki logo

    /**
     * Belirtilen rezervasyon ID'sine göre fatura oluşturur.
     */
    public byte[] generateInvoicePdf(Long reservationId) throws Exception {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        if (optionalReservation.isEmpty()) {
            throw new IllegalArgumentException("Reservation not found.");
        }

        return generateInvoice(optionalReservation.get());
    }

    /**
     * PDF formatında fatura oluşturur.
     */
    public byte[] generateInvoice(Reservation reservation) throws Exception {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        // ✅ **LOGO YÜKLEME**
        try {
            ClassPathResource resource = new ClassPathResource(LOGO_PATH);
            InputStream logoStream = resource.getInputStream();
            Image logo = Image.getInstance(logoStream.readAllBytes());
            logo.scaleToFit(120, 120);
            logo.setAlignment(Element.ALIGN_CENTER);
            document.add(logo);
        } catch (Exception e) {
            System.err.println("Logo yüklenirken hata oluştu: " + e.getMessage());
        }

        // ✅ **FATURA BAŞLIĞI**
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("HOTEL RESERVATION INVOICE", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(15);
        document.add(title);

        // ✅ **FATURA DETAYLARINI TABLO ŞEKLİNDE EKLE**
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

        // **Fatura bilgileri ekleniyor**
        addTableRow(table, "Invoice Number:", "INV-" + reservation.getId(), headerFont);
        addTableRow(table, "Reservation ID:", reservation.getId().toString(), headerFont);
        addTableRow(table, "User Email:", reservation.getUser().getEmail(), headerFont);
        addTableRow(table, "Room Type:", reservation.getRoom().getRoomType(), headerFont);
        addTableRow(table, "Reservation Dates:", reservation.getStartDate() + " - " + reservation.getEndDate(), headerFont);
        addTableRow(table, "Total Price:", "$" + reservation.getTotalPrice(), headerFont);

        document.add(table);

        // ✅ **TEŞEKKÜR MESAJI**
        Paragraph thanks = new Paragraph("Thank you for choosing our hotel!", FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 12));
        thanks.setAlignment(Element.ALIGN_CENTER);
        thanks.setSpacingBefore(20);
        document.add(thanks);

        document.close();
        return baos.toByteArray();
    }

    /**
     * **Tabloya satır eklemek için yardımcı metod**
     */
    private void addTableRow(PdfPTable table, String column1, String column2, Font headerFont) {
        table.addCell(new PdfPCell(new Phrase(column1, headerFont)));
        table.addCell(new PdfPCell(new Phrase(column2)));
    }
}