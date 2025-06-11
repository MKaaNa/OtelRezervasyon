package com.MKaaN.OtelBackend.service.invoice;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.MKaaN.OtelBackend.entity.Reservation;
import com.MKaaN.OtelBackend.repository.ReservationRepository;
import com.MKaaN.OtelBackend.service.spec.InvoiceService;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final ReservationRepository reservationRepository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public InvoiceServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public byte[] generateInvoice(String reservationId) throws Exception {
        return generateInvoicePdf(reservationId);
    }

    @Override
    public byte[] generateInvoicePdf(String reservationId) throws DocumentException {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Add content to the document
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font normalFont = new Font(Font.HELVETICA, 12, Font.NORMAL);

            document.add(new Paragraph("INVOICE", titleFont));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Reservation Details:", normalFont));
            document.add(new Paragraph("Reservation ID: " + reservation.getId(), normalFont));
            document.add(new Paragraph("Room: " + reservation.getRoom().getNumber(), normalFont));
            document.add(new Paragraph("Guest: " + reservation.getUser().getFirstName() + " " + reservation.getUser().getLastName(), normalFont));
            document.add(new Paragraph("Check-in: " + reservation.getStartDate().format(DATE_FORMATTER), normalFont));
            document.add(new Paragraph("Check-out: " + reservation.getEndDate().format(DATE_FORMATTER), normalFont));
            document.add(new Paragraph("Total Amount: " + reservation.getTotalPrice() + " TL", normalFont));

            document.close();
            return out.toByteArray();
        } catch (DocumentException e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }
}
