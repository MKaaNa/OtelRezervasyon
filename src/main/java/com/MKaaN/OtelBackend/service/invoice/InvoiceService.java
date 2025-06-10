package com.MKaaN.OtelBackend.service.invoice;

public interface InvoiceService {
    byte[] generateInvoice(String reservationId) throws Exception;
    byte[] generateInvoicePdf(String reservationId) throws Exception;
} 