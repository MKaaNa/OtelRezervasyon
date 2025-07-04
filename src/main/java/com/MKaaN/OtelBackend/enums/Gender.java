package com.MKaaN.OtelBackend.enums;

public enum Gender {
    MALE("Erkek"),
    FEMALE("Kadın"),
    OTHER("Diğer");

    private final String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 