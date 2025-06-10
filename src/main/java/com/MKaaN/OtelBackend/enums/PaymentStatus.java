package com.MKaaN.OtelBackend.enums;

public enum PaymentStatus {
    PENDING("Bekliyor"),
    COMPLETED("Tamamlandı"),
    FAILED("Başarısız"),
    REFUNDED("İade Edildi"),
    CANCELLED("İptal Edildi");

    private final String displayName;

    PaymentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 