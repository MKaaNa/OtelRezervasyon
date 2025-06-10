package com.MKaaN.OtelBackend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.MKaaN.OtelBackend.enums.RoomType;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private String id;
    private String roomNumber;
    private RoomType roomType;
    private BigDecimal price;
    private boolean available;
    private String description;

    @Min(value = 1, message = "Misafir sayısı en az 1 olmalıdır")
    @Max(value = 10, message = "Misafir sayısı en fazla 10 olabilir")
    private Integer guestCount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters and Setters
    //Builder Design Pattern araştır }
}

