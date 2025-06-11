package com.MKaaN.OtelBackend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.GenericGenerator;

import com.MKaaN.OtelBackend.enums.ReservationStatus;
import com.MKaaN.OtelBackend.util.DateUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotNull
    @Column(name = "check_in_time", nullable = false)
    private LocalTime checkInTime;

    @NotNull
    @Column(name = "check_out_time", nullable = false)
    private LocalTime checkOutTime;

    @NotNull
    @Positive
    @Column(name = "guest_count", nullable = false)
    private int guestCount;

    @NotNull
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(name = "special_requests", length = 1000)
    private String specialRequests;

    @Column(name = "admin_note", length = 1000)
    private String adminNote;

    @Column(name = "cancellation_reason", length = 1000)
    private String cancellationReason;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Constructors
    public Reservation() {
    }

    public Reservation(String id, User user, Room room, LocalDate startDate, LocalDate endDate,
                      LocalTime checkInTime, LocalTime checkOutTime, int guestCount, BigDecimal totalPrice,
                      ReservationStatus status, String specialRequests, String adminNote,
                      String cancellationReason, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.room = room;
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
    }

    // Static Builder method
    public static ReservationBuilder builder() {
        return new ReservationBuilder();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = ReservationStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public boolean isValid() {
        if (startDate == null || endDate == null) {
            return false;
        }
        return endDate.isAfter(startDate);
    }

    public boolean isCancellable() {
        if (status != ReservationStatus.CONFIRMED) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime checkInDateTime = startDate.atTime(checkInTime);
        return now.isBefore(checkInDateTime.minusHours(24)); // 24 saat öncesine kadar iptal edilebilir
    }

    public void cancel(String reason) {
        if (!isCancellable()) {
            throw new IllegalStateException("Rezervasyon iptal edilemez.");
        }
        this.status = ReservationStatus.CANCELLED;
        this.cancellationReason = reason;
        this.updatedAt = LocalDateTime.now();
        calculateCancellationFee();
    }

    private void calculateCancellationFee() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime checkInDateTime = startDate.atTime(checkInTime);
        
        // Check-in'e 24 saatten fazla varsa %20, 24 saatten az varsa %50 iptal ücreti
        if (now.isBefore(checkInDateTime.minusHours(24))) {
            this.totalPrice = totalPrice.multiply(new BigDecimal("0.20"));
        } else {
            this.totalPrice = totalPrice.multiply(new BigDecimal("0.50"));
        }
    }

    public void calculateTotalPrice() {
        if (room != null && startDate != null && endDate != null) {
            this.totalPrice = DateUtils.calculateTotalPrice(startDate, endDate, room.getPrice());
        }
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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

    // Builder class
    public static class ReservationBuilder {
        private String id;
        private User user;
        private Room room;
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

        ReservationBuilder() {
        }

        public ReservationBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ReservationBuilder user(User user) {
            this.user = user;
            return this;
        }

        public ReservationBuilder room(Room room) {
            this.room = room;
            return this;
        }

        public ReservationBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public ReservationBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public ReservationBuilder checkInTime(LocalTime checkInTime) {
            this.checkInTime = checkInTime;
            return this;
        }

        public ReservationBuilder checkOutTime(LocalTime checkOutTime) {
            this.checkOutTime = checkOutTime;
            return this;
        }

        public ReservationBuilder guestCount(int guestCount) {
            this.guestCount = guestCount;
            return this;
        }

        public ReservationBuilder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public ReservationBuilder status(ReservationStatus status) {
            this.status = status;
            return this;
        }

        public ReservationBuilder specialRequests(String specialRequests) {
            this.specialRequests = specialRequests;
            return this;
        }

        public ReservationBuilder adminNote(String adminNote) {
            this.adminNote = adminNote;
            return this;
        }

        public ReservationBuilder cancellationReason(String cancellationReason) {
            this.cancellationReason = cancellationReason;
            return this;
        }

        public ReservationBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ReservationBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Reservation build() {
            return new Reservation(id, user, room, startDate, endDate, checkInTime, checkOutTime, guestCount, 
                                  totalPrice, status, specialRequests, adminNote, cancellationReason, 
                                  createdAt, updatedAt);
        }
    }
}

