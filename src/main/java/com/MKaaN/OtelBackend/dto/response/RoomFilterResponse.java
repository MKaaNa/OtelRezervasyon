package com.MKaaN.OtelBackend.dto.response;

import com.MKaaN.OtelBackend.enums.RoomType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomFilterResponse {
    private String id;
    private String number;
    private RoomType type;
    private BigDecimal price;
    private boolean available;
    private String description;
    private Integer capacity;
} 