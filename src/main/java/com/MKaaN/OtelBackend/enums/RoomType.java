package com.MKaaN.OtelBackend.enums;

public enum RoomType {
    STANDARD("Standart Oda"),
    DELUXE("Deluxe Oda"),
    SUITE("Suit Oda"),
    FAMILY("Aile Odası"),
    EXECUTIVE("Executive Oda");

    private final String displayName;

    RoomType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 