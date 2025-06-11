package com.MKaaN.OtelBackend.dto.request;

import java.time.LocalDate;

import com.MKaaN.OtelBackend.enums.ReservationStatus;

import jakarta.validation.constraints.FutureOrPresent;

/**
 * Rezervasyon güncelleme isteği için kullanılan DTO
 * Tüm alanlar opsiyoneldir, sadece güncellenmek istenenler doldurulur
 */
public class ReservationUpdateRequest {

    private String roomId;

    @FutureOrPresent(message = "Başlangıç tarihi bugün veya gelecekte olmalıdır")
    private LocalDate startDate;

    @FutureOrPresent(message = "Bitiş tarihi bugün veya gelecekte olmalıdır")
    private LocalDate endDate;

    private ReservationStatus status;

    private String adminNote;

    // Default constructor
    public ReservationUpdateRequest() {
    }

    // All args constructor
    public ReservationUpdateRequest(String roomId, LocalDate startDate, LocalDate endDate,
                                  ReservationStatus status, String adminNote) {
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.adminNote = adminNote;
    }

    // Getters
    public String getRoomId() {
        return roomId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public String getAdminNote() {
        return adminNote;
    }

    // Setters
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote;
    }
}
