package com.MKaaN.OtelBackend.enums;

public enum InvoiceMessages {
    TITLE("HOTEL RESERVATION INVOICE"),
    THANK_YOU("Thank you for choosing our hotel!"),
    INVOICE_NUMBER("Invoice Number:"),
    RESERVATION_ID("Reservation ID:"),
    USER_EMAIL("User Email:"),
    ROOM_TYPE("Room Type:"),
    RESERVATION_DATES("Reservation Dates:"),
    TOTAL_PRICE("Total Price:"),
    RESERVATION_NOT_FOUND("Reservation not found."),
    LOGO_LOAD_ERROR("Logo yüklenirken hata oluştu: %s");

    private final String message;

    InvoiceMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getFormattedMessage(Object... args) {
        return String.format(message, args);
    }
} 