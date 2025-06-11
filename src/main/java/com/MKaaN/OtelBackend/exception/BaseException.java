package com.MKaaN.OtelBackend.exception;

public class BaseException extends RuntimeException {
    private String message;
    private String code;

    public BaseException(String message, String code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
} 