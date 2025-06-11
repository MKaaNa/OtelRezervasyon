package com.MKaaN.OtelBackend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.MKaaN.OtelBackend.enums.RoomType;
import com.MKaaN.OtelBackend.entity.Room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Oda Response sınıfı
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {
    private String id;
    private String number;
    private RoomType type;
    private BigDecimal price;
    private boolean available;
    private String description;
    private Integer capacity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters
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

    public boolean isAvailable() {
        return available;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Room entity'sinden RoomResponse'a dönüşüm yapar
     *
     * @param room Room entity
     * @return RoomResponse
     */
    public static RoomResponse fromRoom(Room room) {
        if (room == null) {
            return null;
        }

        RoomResponse response = new RoomResponse();
        response.setId(room.getId());
        response.setNumber(room.getNumber());
        response.setType(room.getType());
        response.setPrice(room.getPrice());
        response.setAvailable(room.isAvailable());
        response.setDescription(room.getDescription());
        response.setCapacity(room.getCapacity());
        response.setCreatedAt(room.getCreatedAt());
        response.setUpdatedAt(room.getUpdatedAt());
        return response;
    }
}
