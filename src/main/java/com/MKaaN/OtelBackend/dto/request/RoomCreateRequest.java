package com.MKaaN.OtelBackend.dto.request;

import java.math.BigDecimal;

import com.MKaaN.OtelBackend.enums.RoomType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Oda oluşturma isteği için DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomCreateRequest {

    @NotBlank(message = "Room number cannot be empty")
    private String number;

    @NotNull(message = "Room type cannot be null")
    private RoomType type;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    private String description;

    @NotNull(message = "Capacity cannot be null")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;
}
