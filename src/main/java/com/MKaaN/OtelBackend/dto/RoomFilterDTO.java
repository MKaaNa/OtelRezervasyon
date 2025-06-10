package com.MKaaN.OtelBackend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RoomFilterDTO {
    private String type;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer guestCount;
    private LocalDate startDate;
    private LocalDate endDate;

    public RoomFilterDTO() {
    }

    public RoomFilterDTO(String type, BigDecimal minPrice, BigDecimal maxPrice, Integer guestCount, LocalDate startDate, LocalDate endDate) {
        this.type = type;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.guestCount = guestCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(Integer guestCount) {
        this.guestCount = guestCount;
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
} 