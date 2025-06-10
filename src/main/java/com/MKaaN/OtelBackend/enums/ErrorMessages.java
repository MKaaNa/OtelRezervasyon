package com.MKaaN.OtelBackend.enums;

public enum ErrorMessages {
    INVALID_CREDENTIALS("Geçersiz kullanıcı adı veya şifre"),
    EMAIL_ALREADY_EXISTS("Bu email ile zaten bir kullanıcı var"),
    ROOM_TYPE_NOT_FOUND("Oda tipi bulunamadı: %s"),
    RESOURCE_NOT_FOUND("İstenen kaynak bulunamadı"),
    INVALID_REQUEST("Geçersiz istek"),
    ACCESS_DENIED("Bu işlem için yetkiniz bulunmamaktadır"),
    INTERNAL_SERVER_ERROR("Beklenmeyen bir hata oluştu"),
    VALIDATION_ERROR("Validasyon hatası");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getFormattedMessage(Object... args) {
        return String.format(message, args);
    }
} 