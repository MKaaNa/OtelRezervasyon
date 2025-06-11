package com.MKaaN.OtelBackend.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.MKaaN.OtelBackend.enums.RoomType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.GenerationType;

@Data
@Entity
@Table(name = "rooms")
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType type;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private boolean available;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Integer capacity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Özel metodlar
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean canAccommodateGuests(int guestCount) {
        return guestCount <= capacity;
    }

    public boolean needsCleaning() {
        if (createdAt == null) {
            return true;
        }
        return LocalDateTime.now().isAfter(createdAt.plusDays(1));
    }

    public boolean needsMaintenance() {
        if (updatedAt == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(updatedAt);
    }

    public void markAsCleaned() {
        this.createdAt = LocalDateTime.now();
    }

    public void scheduleMaintenance(LocalDateTime maintenanceDate) {
        this.updatedAt = maintenanceDate;
    }

    public String getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public RoomType getType() {
        return type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isHasBalcony() {
        return false; // Assuming the default value is false
    }

    public boolean isHasSeaView() {
        return false; // Assuming the default value is false
    }

    public boolean isHasAirConditioning() {
        return false; // Assuming the default value is false
    }

    public boolean isHasMinibar() {
        return false; // Assuming the default value is false
    }

    public boolean isHasTv() {
        return false; // Assuming the default value is false
    }

    public boolean isHasSafe() {
        return false; // Assuming the default value is false
    }

    public boolean isHasWifi() {
        return false; // Assuming the default value is false
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}