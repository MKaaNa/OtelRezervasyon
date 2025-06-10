package com.MKaaN.OtelBackend.enums;

public enum PaymentMethod {
    CREDIT_CARD("Kredi Kartı"),
    DEBIT_CARD("Banka Kartı"),
    BANK_TRANSFER("Banka Havalesi"),
    CASH("Nakit"),
    PAYPAL("PayPal");

    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 