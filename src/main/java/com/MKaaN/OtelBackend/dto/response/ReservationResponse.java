package com.MKaaN.OtelBackend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.MKaaN.OtelBackend.entity.Reservation;
import com.MKaaN.OtelBackend.enums.ReservationStatus;

/**
 * Rezervasyon bilgilerini görüntülemek için kullanılan DTO
 */
public class ReservationResponse {
    private String id;
    private String userId;
    private String roomId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private int guestCount;
    private BigDecimal totalPrice;
    private ReservationStatus status;
    private String specialRequests;
    private String adminNote;
    private String cancellationReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserResponse user;
    private RoomResponse room;

    // Default constructor
    public ReservationResponse() {
    }

    // All args constructor
    public ReservationResponse(String id, String userId, String roomId, LocalDate startDate, LocalDate endDate,
                               LocalTime checkInTime, LocalTime checkOutTime, int guestCount, BigDecimal totalPrice,
                               ReservationStatus status, String specialRequests, String adminNote,
                               String cancellationReason, LocalDateTime createdAt, LocalDateTime updatedAt,
                               UserResponse user, RoomResponse room) {
        this.id = id;
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
        this.cancellationReason = cancellationReason;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.room = room;
    }

    // Static builder method
    public static ReservationResponseBuilder builder() {
        return new ReservationResponseBuilder();
    }

    /**
     * Reservation entity'sinden ReservationResponse'a dönüşüm yapar
     * İlişkili entity'leri (User, Room) içermez
     *
     * @param reservation Reservation entity
     * @return ReservationResponse
     */
    public static ReservationResponse fromEntity(Reservation reservation) {
        if (reservation == null) {
            return null;
        }

        return ReservationResponse.builder()
                .id(reservation.getId())
                .roomId(reservation.getRoom() != null ? reservation.getRoom().getId() : null)
                .userId(reservation.getUser() != null ? reservation.getUser().getId() : null)
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .checkInTime(reservation.getCheckInTime())
                .checkOutTime(reservation.getCheckOutTime())
                .totalPrice(reservation.getTotalPrice())
                .status(reservation.getStatus())
                .adminNote(reservation.getAdminNote())
                .cancellationReason(reservation.getCancellationReason())
                .createdAt(reservation.getCreatedAt())
                .updatedAt(reservation.getUpdatedAt())
                .build();
    }

    /**
     * Reservation entity'sinden ReservationResponse'a dönüşüm yapar
     * İlişkili entity'leri (User, Room) de içerir
     *
     * @param reservation Reservation entity
     * @param includeUserDetails User detayları eklensin mi
     * @param includeRoomDetails Room detayları eklensin mi
     * @return ReservationResponse
     */
    public static ReservationResponse fromEntityWithDetails(Reservation reservation,
                                                           boolean includeUserDetails,
                                                           boolean includeRoomDetails) {
        if (reservation == null) {
            return null;
        }

        ReservationResponse response = fromEntity(reservation);

        if (includeUserDetails && reservation.getUser() != null) {
            response.setUser(UserResponse.fromUser(reservation.getUser()));
        }

        if (includeRoomDetails && reservation.getRoom() != null) {
            response.setRoom(RoomResponse.fromRoom(reservation.getRoom()));
        }

        return response;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public LocalTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public int getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public String getAdminNote() {
        return adminNote;
    }

    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public RoomResponse getRoom() {
        return room;
    }

    public void setRoom(RoomResponse room) {
        this.room = room;
    }

    // Builder class
    public static class ReservationResponseBuilder {
        private String id;
        private String userId;
        private String roomId;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalTime checkInTime;
        private LocalTime checkOutTime;
        private int guestCount;
        private BigDecimal totalPrice;
        private ReservationStatus status;
        private String specialRequests;
        private String adminNote;
        private String cancellationReason;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private UserResponse user;
        private RoomResponse room;

        ReservationResponseBuilder() {
        }

        public ReservationResponseBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ReservationResponseBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public ReservationResponseBuilder roomId(String roomId) {
            this.roomId = roomId;
            return this;
        }

        public ReservationResponseBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public ReservationResponseBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public ReservationResponseBuilder checkInTime(LocalTime checkInTime) {
            this.checkInTime = checkInTime;
            return this;
        }

        public ReservationResponseBuilder checkOutTime(LocalTime checkOutTime) {
            this.checkOutTime = checkOutTime;
            return this;
        }

        public ReservationResponseBuilder guestCount(int guestCount) {
            this.guestCount = guestCount;
            return this;
        }

        public ReservationResponseBuilder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public ReservationResponseBuilder status(ReservationStatus status) {
            this.status = status;
            return this;
        }

        public ReservationResponseBuilder specialRequests(String specialRequests) {
            this.specialRequests = specialRequests;
            return this;
        }

        public ReservationResponseBuilder adminNote(String adminNote) {
            this.adminNote = adminNote;
            return this;
        }

        public ReservationResponseBuilder cancellationReason(String cancellationReason) {
            this.cancellationReason = cancellationReason;
            return this;
        }

        public ReservationResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ReservationResponseBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ReservationResponseBuilder user(UserResponse user) {
            this.user = user;
            return this;
        }

        public ReservationResponseBuilder room(RoomResponse room) {
            this.room = room;
            return this;
        }

        public ReservationResponse build() {
            return new ReservationResponse(id, userId, roomId, startDate, endDate, checkInTime, checkOutTime,
                                          guestCount, totalPrice, status, specialRequests, adminNote,
                                          cancellationReason, createdAt, updatedAt, user, room);
        }
    }
}
