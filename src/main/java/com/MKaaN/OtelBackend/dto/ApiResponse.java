package com.MKaaN.OtelBackend.dto;

import java.time.LocalDateTime;

public class ApiResponse<T> {
    private boolean success;
    private T data;
    private ErrorResponse error;

    private ApiResponse(boolean success, T data, ErrorResponse error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static <T> ApiResponse<T> error(ErrorResponse error) {
        return new ApiResponse<>(false, null, error);
    }

    public static <T> ApiResponse<T> error(String message) {
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            message,
            "ERROR",
            null
        );
        return new ApiResponse<>(false, null, errorResponse);
    }

    public static <T> ApiResponse<T> errorWithValidationErrors(String message, Object validationErrors) {
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            message,
            "VALIDATION_ERROR",
            null
        );
        return new ApiResponse<>(false, null, errorResponse);
    }

    public static <T> ApiResponse<T> errorWithData(String message, T data) {
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            message,
            "ERROR",
            null
        );
        return new ApiResponse<>(false, data, errorResponse);
    }

    public static <T> ApiResponse<T> errorWithStatus(String message, int status) {
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            message,
            String.valueOf(status),
            null
        );
        return new ApiResponse<>(false, null, errorResponse);
    }

    public static <T> ApiResponse<T> notFound(String message) {
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            message,
            "404",
            null
        );
        return new ApiResponse<>(false, null, errorResponse);
    }

    public static <T> ApiResponse<T> unauthorized(String message) {
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            message,
            "401",
            null
        );
        return new ApiResponse<>(false, null, errorResponse);
    }
} 