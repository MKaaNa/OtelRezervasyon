package com.MKaaN.OtelBackend.controller;

import com.MKaaN.OtelBackend.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping(value = "/{reservationId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<byte[]> getInvoiceTxt(@PathVariable Long reservationId) {
        try {
            byte[] txtBytes = invoiceService.generateInvoiceTxt(reservationId);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice_" + reservationId + ".txt")
                    .body(txtBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}