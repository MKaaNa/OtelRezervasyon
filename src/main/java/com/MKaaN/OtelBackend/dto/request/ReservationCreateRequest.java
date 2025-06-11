package com.MKaaN.OtelBackend.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import com.MKaaN.OtelBackend.enums.ReservationStatus;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Rezervasyon oluşturma isteği için kullanılan DTO
 */
public class ReservationCreateRequest {

    @NotBlank(message = "Kullanıcı ID boş olamaz")
    private String userId;

    @NotBlank(message = "Oda ID boş olamaz")
    private String roomId;

    @NotNull(message = "Başlangıç tarihi boş olamaz")
    @FutureOrPresent(message = "Başlangıç tarihi bugün veya gelecekte olmalıdır")
    private LocalDate startDate;

    @NotNull(message = "Bitiş tarihi boş olamaz")
    @FutureOrPresent(message = "Bitiş tarihi bugün veya gelecekte olmalıdır")
    private LocalDate endDate;

    private LocalTime checkInTime;

    private LocalTime checkOutTime;

    private Integer guestCount;

    private BigDecimal totalPrice;

    private ReservationStatus status;

    private String specialRequests;

    private String adminNote;

    // Default constructor
    public ReservationCreateRequest() {
    }

    // All args constructor
    public ReservationCreateRequest(String userId, String roomId, LocalDate startDate,
                                  LocalDate endDate, LocalTime checkInTime, LocalTime checkOutTime,
                                  Integer guestCount, BigDecimal totalPrice, ReservationStatus status,
                                  String specialRequests, String adminNote) {
        this.userId = userId;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.guestCount = guestCount;
        this.totalPrice = totalPrice;
        this.status = status;
        this.specialRequests = specialRequests;
        this.adminNote = adminNote;
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalTime getCheckInTime() {
        return checkInTime;
    }

    public LocalTime getCheckOutTime() {
        return checkOutTime;
    }

    public Integer getGuestCount() {
        return guestCount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public String getAdminNote() {
        return adminNote;
    }

    // Setters
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setCheckInTime(LocalTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public void setCheckOutTime(LocalTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public void setGuestCount(Integer guestCount) {
        this.guestCount = guestCount;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote;
    }
}
