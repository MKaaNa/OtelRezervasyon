package com.MKaaN.OtelBackend.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import com.MKaaN.OtelBackend.enums.RoomType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "rooms")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id;
    
    @NotBlank
    @Column(name = "room_number", unique = true)
    private String roomNumber;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private RoomType roomType;
    
    @NotNull
    @Positive
    private BigDecimal price;
    
    @Column(nullable = false)
    private boolean available;
    
    @Column(length = 1000)
    private String description;
    
    @Column(nullable = false)
    private Integer guestCount;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    public Room() {
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(Integer guestCount) {
        this.guestCount = guestCount;
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

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public static RoomBuilder builder() {
        return new RoomBuilder();
    }

    public static class RoomBuilder {
        private String id;
        private String roomNumber;
        private RoomType roomType;
        private BigDecimal price;
        private boolean available;
        private Integer guestCount;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<Reservation> reservations;

        public RoomBuilder id(String id) {
            this.id = id;
            return this;
        }

        public RoomBuilder roomNumber(String roomNumber) {
            this.roomNumber = roomNumber;
            return this;
        }

        public RoomBuilder roomType(RoomType roomType) {
            this.roomType = roomType;
            return this;
        }

        public RoomBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public RoomBuilder available(boolean available) {
            this.available = available;
            return this;
        }

        public RoomBuilder guestCount(Integer guestCount) {
            this.guestCount = guestCount;
            return this;
        }

        public RoomBuilder description(String description) {
            this.description = description;
            return this;
        }

        public RoomBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public RoomBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public RoomBuilder reservations(List<Reservation> reservations) {
            this.reservations = reservations;
            return this;
        }

        public Room build() {
            Room room = new Room();
            room.setId(id);
            room.setRoomNumber(roomNumber);
            room.setRoomType(roomType);
            room.setPrice(price);
            room.setAvailable(available);
            room.setGuestCount(guestCount);
            room.setDescription(description);
            room.setCreatedAt(createdAt);
            room.setUpdatedAt(updatedAt);
            room.setReservations(reservations);
            return room;
        }
    }
}