package com.MKaaN.OtelBackend.dto.request;

import java.time.LocalDate;

/**
 * Fiyat hesaplama isteği için DTO
 */
public class PriceCalculationRequest {
    private String roomId;
    private LocalDate startDate;
    private LocalDate endDate;

    // Default constructor
    public PriceCalculationRequest() {
    }

    // All args constructor
    public PriceCalculationRequest(String roomId, LocalDate startDate, LocalDate endDate) {
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getter ve Setter metodları
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
