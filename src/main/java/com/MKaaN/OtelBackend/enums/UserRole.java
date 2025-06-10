package com.MKaaN.OtelBackend.enums;

public enum UserRole {
    USER("Kullanıcı"),
    ADMIN("Yönetici"),
    STAFF("Personel");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
