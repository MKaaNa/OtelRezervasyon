package com.MKaaN.OtelBackend.service;

public class Invoice {
    private Long reservationId;
    private String invoiceNumber;
    private String userEmail;
    private String paymentDate;  // ISO formatında tarih
    private double totalAmount;
    private String details;

    public Invoice(Long reservationId, String invoiceNumber, String userEmail, String paymentDate, double totalAmount, String details) {
        this.reservationId = reservationId;
        this.invoiceNumber = invoiceNumber;
        this.userEmail = userEmail;
        this.paymentDate = paymentDate;
        this.totalAmount = totalAmount;
        this.details = details;
    }

    public Invoice(Long id) {
    }

    // Getter ve Setter'lar...
    public Long getReservationId() { return reservationId; }
    public void setReservationId(Long reservationId) { this.reservationId = reservationId; }

    public String getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}