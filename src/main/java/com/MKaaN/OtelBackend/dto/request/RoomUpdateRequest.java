package com.MKaaN.OtelBackend.dto.request;

import com.MKaaN.OtelBackend.enums.RoomType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Oda güncelleme isteği için DTO
 * Tüm alanlar opsiyoneldir, sadece güncellenmek istenenler doldurulur
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomUpdateRequest {
    @NotBlank(message = "Room ID cannot be empty")
    private String id;

    @NotBlank(message = "Room number cannot be empty")
    private String number;

    @NotNull(message = "Room type cannot be null")
    private RoomType type;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    private boolean available;

    private String description;

    @NotNull(message = "Capacity cannot be null")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;
}
