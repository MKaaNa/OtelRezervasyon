package com.MKaaN.OtelBackend.enums;

public enum EmailMessages {
    FROM_ADDRESS("noreply@otelrezervasyon.com"),
    RESERVATION_REMINDER_SUBJECT("Reservation Reminder"),
    RESERVATION_REMINDER_TEXT("Dear Customer,\n\nThis is a reminder that your reservation starting on %s is coming up.\n\nThank you.");

    private final String message;

    EmailMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getFormattedMessage(Object... args) {
        return String.format(message, args);
    }
} 