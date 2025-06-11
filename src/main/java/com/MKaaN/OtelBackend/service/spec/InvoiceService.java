package com.MKaaN.OtelBackend.service.spec;

public interface InvoiceService {
    byte[] generateInvoice(String reservationId) throws Exception;
    byte[] generateInvoicePdf(String reservationId) throws Exception;
}
