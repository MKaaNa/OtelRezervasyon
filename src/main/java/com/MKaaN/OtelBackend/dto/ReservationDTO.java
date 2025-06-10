package com.MKaaN.OtelBackend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.MKaaN.OtelBackend.enums.ReservationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private String id;
    private String userId;
    private String roomId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalPrice;
    private ReservationStatus status;
    private String adminNote;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }
    public String getAdminNote() { return adminNote; }
    public void setAdminNote(String adminNote) { this.adminNote = adminNote; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static ReservationDTOBuilder builder() {
        return new ReservationDTOBuilder();
    }

    public static class ReservationDTOBuilder {
        private String id;
        private String roomId;
        private String userId;
        private LocalDate startDate;
        private LocalDate endDate;
        private ReservationStatus status;
        private BigDecimal totalPrice;
        private String adminNote;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public ReservationDTOBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ReservationDTOBuilder roomId(String roomId) {
            this.roomId = roomId;
            return this;
        }

        public ReservationDTOBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public ReservationDTOBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public ReservationDTOBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public ReservationDTOBuilder status(ReservationStatus status) {
            this.status = status;
            return this;
        }

        public ReservationDTOBuilder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public ReservationDTOBuilder adminNote(String adminNote) {
            this.adminNote = adminNote;
            return this;
        }

        public ReservationDTOBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ReservationDTOBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ReservationDTO build() {
            ReservationDTO dto = new ReservationDTO();
            dto.setId(id);
            dto.setRoomId(roomId);
            dto.setUserId(userId);
            dto.setStartDate(startDate);
            dto.setEndDate(endDate);
            dto.setStatus(status);
            dto.setTotalPrice(totalPrice);
            dto.setAdminNote(adminNote);
            dto.setCreatedAt(createdAt);
            dto.setUpdatedAt(updatedAt);
            return dto;
        }
    }
}